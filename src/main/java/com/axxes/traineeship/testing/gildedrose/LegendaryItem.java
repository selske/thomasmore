package com.axxes.traineeship.testing.gildedrose;

public class LegendaryItem extends EnhancedItem {

    public LegendaryItem(Item item) {
        super(item);
    }

    @Override
    public void updateQuality() {
        // legendaries are forever
    }

}
