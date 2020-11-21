package com.aeon.app;

import android.util.Log;

import com.aeon.app.models.Node;
import com.aeon.app.models.TransactionInfo;
import com.aeon.app.models.TransactionPending;
import com.aeon.app.models.Wallet;
import com.aeon.app.ui.node.NodeContent;
import com.aeon.app.ui.recent.RecentContent;
import com.aeon.app.ui.wallet.WalletContent;

import java.util.ArrayList;

public class BackgroundThread extends Thread{
    private static final String TAG = "BackgroundThread";
    private static Node node;
    private static Wallet wallet = null;
    private static int sleepTimer;
    private static ArrayList<TransactionPending> pendingOutTransactions = new ArrayList<TransactionPending>();
    private static boolean isNodeChanged;
    public static boolean isRunning = false;
    public static boolean isManaging = false;
    public static boolean isShownNewWalletFragment = false;
    public static String path = null;
    public static String seed = null;
    private int txIndex=0;

    public BackgroundThread() {

    }

    public void run() {
        isRunning = true;
        Log.v(TAG, "isRunning");
        while(!Thread.interrupted()){
            Log.v(TAG, "!Thread.interrupted()");
            if(isManaging && ! wallet.isClosed) {
                Log.v(TAG, "isManaging && !wallet.isClosed");
                if (wallet.isExists == false) {
                    Log.v(TAG, "!wallet.isExists");
                    wallet.create();
                    this.seed = wallet.seed;
                } else if (wallet.isInit == false) {
                    Log.v(TAG, "!wallet.isInit");
                    connectToNode();
                    init();
                } else {
                    Log.v(TAG, "wallet.isExists && wallet.isInit");
                    sleepTimer=10000;
                    updateTransactions();
                    refreshWalletInfo();
                    refreshNode();
                    clearTransactionQueue();
                }
            }
            try {
                Log.v(TAG, "Thread.sleep");
                Thread.sleep(sleepTimer);
            } catch (InterruptedException e) {
                this.interrupt();
                e.printStackTrace();
            }
        }
        Log.v(TAG, "Thread.interrupted()");
        wallet.close();
        isManaging = false;
        isShownNewWalletFragment=false;
        isRunning = false;
        Log.v(TAG, "!isManaging");
        WalletContent.clearItems();
        sleepTimer = 200;
        Log.v(TAG, "!isRunning");
    }

    public static void queueWallet(String path){
        Log.v(TAG, "queueWallet");
        BackgroundThread.path = path;
        BackgroundThread.seed = null;
        wallet = new Wallet(path,"test","English");
        isManaging = true;
        sleepTimer = 200;
    }
    public static void queueWallet(String path, String seed, int restoreHeight){
        Log.v(TAG, "queueWallet");
        BackgroundThread.path = path;
        BackgroundThread.seed = null;
        wallet = new Wallet(path,"test","English", seed, restoreHeight);
        isManaging = true;
        sleepTimer = 200;
    }

    public static void queueWallet(String path, String account, String view, String spend, int restoreHeight) {
        Log.v(TAG, "queueWallet");
        BackgroundThread.path = path;
        BackgroundThread.seed = null;
        wallet = new Wallet(path,"test","English", account, view, spend, restoreHeight);
        isManaging = true;
        sleepTimer = 200;
    }

    public static void queueTransaction(String dst_address, long amount){
        Log.v(TAG, "queueTransaction");
        pendingOutTransactions.add(new TransactionPending(dst_address,amount));
    }

    public static void setAddressIndex(int index){
        Log.v(TAG, "setAddressIndex");
        wallet.setAddressIndex(index);
    }


    public static void setNode(Node node) {
        Log.v(TAG, "setNode");
        BackgroundThread.node = node;
        isNodeChanged = true;
    }

    private void clearTransactionQueue(){
        Log.v(TAG, "clearTransactionQueue");
        ArrayList<TransactionPending> clone = new ArrayList<>();
        clone.addAll(pendingOutTransactions);
        for(TransactionPending tx : clone){
            if(!tx.isCommitted && !tx.isAttempted){
                tx.setHandle(wallet.createTransaction(tx));
                tx.commit();
            } else {
                tx.refresh();
                switch(tx.status){
                    case Ok:
                    case Error:
                    case Critical:
                        wallet.disposeTransaction(tx);
                        pendingOutTransactions.remove(tx);
                        break;
                    default:

                }
            }
        }
    }

    public void updateTransactions(){
        Log.v(TAG, "updateTransactions");
        for(TransactionInfo tx : wallet.transactions) {
            tx.refresh();
        }
        RecentContent.updateItems(wallet.transactions);
    }

    private void init(){
        Log.v(TAG, "init");
        wallet.init();
        WalletContent.clearItems();
        WalletContent.addItem(new WalletContent.Item("Name", wallet.name));
        WalletContent.addItem(new WalletContent.Item("Account", wallet.account));
        WalletContent.addItem(new WalletContent.Item("Receiving Address", wallet.address));
        WalletContent.addItem(new WalletContent.Item("Address Index", String.valueOf(wallet.index)));
        WalletContent.addItem(new WalletContent.Item("Balance Total", "?"));
        WalletContent.addItem(new WalletContent.Item("Balance Spendable",  "?"));
        WalletContent.addItem(new WalletContent.Item("Synchronized", String.valueOf(wallet.isSynchronized)));
        WalletContent.addItem(new WalletContent.Item("Status", String.valueOf(wallet.status)));
        WalletContent.addItem(new WalletContent.Item("Path", wallet.path));
        WalletContent.addItem(new WalletContent.Item("Transaction Count", String.valueOf(0)));
        WalletContent.addItem(new WalletContent.Item("Public Spend Key",wallet.publicSpendKey));
        WalletContent.addItem(new WalletContent.Item("Public View Key",wallet.publicViewKey));
        WalletContent.addItem(new WalletContent.Item("Seed",wallet.seed));
        WalletContent.addItem(new WalletContent.Item("Secret Spend Key",wallet.secretSpendKey));
        WalletContent.addItem(new WalletContent.Item("Secret View Key",wallet.secretViewKey));
        NodeContent.updateItem("Version",String.valueOf(wallet.node.version));
    }


    private void connectToNode(){
        Log.v(TAG, "connectToNode");
        if(wallet.node == null){
            if(BackgroundThread.node==null){
                BackgroundThread.node = Node.pickRandom();
            }
            wallet.setNode(BackgroundThread.node);
            isNodeChanged = false;
            NodeContent.clearItems();
            NodeContent.addItem(new NodeContent.Item("IP Address",node.hostAddress+":"+node.hostPort));
            NodeContent.addItem(new NodeContent.Item("Height","?"));
            NodeContent.addItem(new NodeContent.Item("Status","?"));
            NodeContent.addItem(new NodeContent.Item("Target","?"));
            NodeContent.addItem(new NodeContent.Item("Version","?"));
        }
    }
    public void refreshWalletInfo(){
        Log.v(TAG, "refreshWalletInfo");
        wallet.refresh();
        WalletContent.updateItem("Receiving Address", wallet.address);
        WalletContent.updateItem("Address Index", String.valueOf(wallet.index));
        WalletContent.updateItem("Transaction Count", String.valueOf(wallet.transactionHistory.count));
        WalletContent.updateItem("Balance Total", wallet.balance.toPlainString());
        WalletContent.updateItem("Balance Spendable", wallet.unlockedBalance.toPlainString());
        WalletContent.updateItem("Synchronized", String.valueOf(wallet.isSynchronized));
        WalletContent.updateItem("Status", String.valueOf(wallet.status));
    }
    public void refreshNode(){
        Log.v(TAG, "refreshNode");
        NodeContent.updateItem("Height", String.valueOf(wallet.node.height));
        NodeContent.updateItem("Target", String.valueOf(wallet.node.target));
        NodeContent.updateItem("Status", String.valueOf(wallet.connectionStatus));
        NodeContent.updateItem("Version",String.valueOf(wallet.node.version));
        if(isNodeChanged){
            wallet.setNode(BackgroundThread.node);
            NodeContent.updateItem("IP Address", BackgroundThread.node.hostAddress+":"+ BackgroundThread.node.hostPort);
        }
    }
}