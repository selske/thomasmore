package com.axxes.traineeship.testing.gildedrose;

public class ItemEnhancer {

    public static EnhancedItem enhance(Item item) {
        if (item.name.equals("Sulfuras, Hand of Ragnaros")) {
            return new LegendaryItem(item);
        } else if (item.name.equals("Aged Brie")) {
            return new AgedBrieItem(item);
        } else {
            return new EnhancedItem(item);
        }
    }

}
