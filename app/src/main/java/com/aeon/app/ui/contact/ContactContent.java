package com.aeon.app.ui.contact;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactContent {

    public static final List<Contact> ITEMS = new ArrayList<Contact>();
    public static final Map<String, Contact> ITEM_MAP = new HashMap<String, Contact>();

    public static void addItem(Contact item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.name, item);
        if(ContactFragment.contactAdapter!=null) {
            ContactFragment.contactAdapter.notifyDataSetChanged();
        }
    }

    public static class Contact {
        public final String name;
        public final String address;

        public Contact(String name, String address) {
            this.name = name;
            this.address = address;
        }

        @Override
        public String toString() {
            return address;
        }
    }
}