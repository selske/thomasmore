package com.axxes.traineeship.testing.gildedrose;

import java.util.Arrays;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }

    public void updateQuality() {
        Arrays.stream(items)
                .map(ItemEnhancer::enhance)
                .forEach(EnhancedItem::updateQuality);
    }

}
