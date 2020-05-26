package com.axxes.traineeship.testing.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class GildedRoseTest {

    @Test
    void sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 5, 7);

        GildedRose gr = new GildedRose(new Item[]{ (sulfuras) });

        for (int i = 0; i < 10; i++) {
            gr.updateQuality();

            assertSoftly(softly -> {
                softly.assertThat(sulfuras.name).isEqualTo("Sulfuras, Hand of Ragnaros");
                softly.assertThat(sulfuras.sellIn).isEqualTo(5);
                softly.assertThat(sulfuras.quality).isEqualTo(7);
            });
        }
    }

    @Test
    void regularItem() {
        Item farBeforeSellIn = new Item("Far before sellIn", 7, 10);
        Item beforeSellIn = new Item("Before sellIn", 1, 10);
        Item onSellIn = new Item("On sellIn", 0, 10);
        Item afterSellIn = new Item("After sellIn", -1, 10);
        Item farAfterSellIn = new Item("Far after sellIn", -7, 10);
        Item minQuality = new Item("Min Quality", -7, 0);

        Item[] items = new Item[]{ farBeforeSellIn, beforeSellIn, onSellIn, afterSellIn, farAfterSellIn, minQuality };

        GildedRose gr = new GildedRose(items);

        gr.updateQuality();

        assertSoftly(softly -> {
            softly.assertThat(farBeforeSellIn.sellIn).isEqualTo(6);
            softly.assertThat(farBeforeSellIn.quality).isEqualTo(9);

            softly.assertThat(beforeSellIn.sellIn).isEqualTo(0);
            softly.assertThat(beforeSellIn.quality).isEqualTo(9);

            softly.assertThat(onSellIn.sellIn).isEqualTo(-1);
            softly.assertThat(onSellIn.quality).isEqualTo(8);

            softly.assertThat(afterSellIn.sellIn).isEqualTo(-2);
            softly.assertThat(afterSellIn.quality).isEqualTo(8);

            softly.assertThat(farAfterSellIn.sellIn).isEqualTo(-8);
            softly.assertThat(farAfterSellIn.quality).isEqualTo(8);

            softly.assertThat(minQuality.sellIn).isEqualTo(-8);
            softly.assertThat(minQuality.quality).isEqualTo(0);
        });
    }

    @Test
    void agedBrie() {
        Item farBeforeSellIn = new Item("Aged Brie", 7, 10);
        Item beforeSellIn = new Item("Aged Brie", 1, 10);
        Item onSellIn = new Item("Aged Brie", 0, 10);
        Item afterSellIn = new Item("Aged Brie", -1, 10);
        Item farAfterSellIn = new Item("Aged Brie", -7, 10);
        Item maxQuality = new Item("Aged Brie", -7, 50);

        Item[] items = new Item[]{ farBeforeSellIn, beforeSellIn, onSellIn, afterSellIn, farAfterSellIn, maxQuality };

        GildedRose gr = new GildedRose(items);

        gr.updateQuality();

        assertSoftly(softly -> {
            softly.assertThat(farBeforeSellIn.sellIn).isEqualTo(6);
            softly.assertThat(farBeforeSellIn.quality).isEqualTo(11);

            softly.assertThat(beforeSellIn.sellIn).isEqualTo(0);
            softly.assertThat(beforeSellIn.quality).isEqualTo(11);

            softly.assertThat(onSellIn.sellIn).isEqualTo(-1);
            softly.assertThat(onSellIn.quality).isEqualTo(12);

            softly.assertThat(afterSellIn.sellIn).isEqualTo(-2);
            softly.assertThat(afterSellIn.quality).isEqualTo(12);

            softly.assertThat(farAfterSellIn.sellIn).isEqualTo(-8);
            softly.assertThat(farAfterSellIn.quality).isEqualTo(12);

            softly.assertThat(maxQuality.sellIn).isEqualTo(-8);
            softly.assertThat(maxQuality.quality).isEqualTo(50);
        });
    }

    @Test
    void backstagePass() {
        Item moreThan10DaysBefore = new Item("Backstage passes to a TAFKAL80ETC concert", 17, 10);
        Item exactly10DaysBefore = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 10);
        Item moreThan5DaysBefore = new Item("Backstage passes to a TAFKAL80ETC concert", 7, 10);
        Item exactly5DaysBefore = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 10);
        Item lessThan5DaysBefore = new Item("Backstage passes to a TAFKAL80ETC concert", 2, 10);
        Item atConcertDate = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 10);
        Item afterConcertDate = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 10);
        Item farAfterConcertDate = new Item("Backstage passes to a TAFKAL80ETC concert", -7, 10);
        Item maxQuality = new Item("Backstage passes to a TAFKAL80ETC concert", 7, 50);

        Item[] items = new Item[]{ moreThan10DaysBefore, exactly10DaysBefore, moreThan5DaysBefore, exactly5DaysBefore, lessThan5DaysBefore, atConcertDate, afterConcertDate, farAfterConcertDate, maxQuality };

        GildedRose gr = new GildedRose(items);

        gr.updateQuality();

        assertSoftly(softly -> {
            softly.assertThat(moreThan10DaysBefore.sellIn).isEqualTo(16);
            softly.assertThat(moreThan10DaysBefore.quality).isEqualTo(11);

            softly.assertThat(exactly10DaysBefore.sellIn).isEqualTo(9);
            softly.assertThat(exactly10DaysBefore.quality).isEqualTo(12);

            softly.assertThat(moreThan5DaysBefore.sellIn).isEqualTo(6);
            softly.assertThat(moreThan5DaysBefore.quality).isEqualTo(12);

            softly.assertThat(exactly5DaysBefore.sellIn).isEqualTo(4);
            softly.assertThat(exactly5DaysBefore.quality).isEqualTo(13);

            softly.assertThat(lessThan5DaysBefore.sellIn).isEqualTo(1);
            softly.assertThat(lessThan5DaysBefore.quality).isEqualTo(13);

            softly.assertThat(atConcertDate.sellIn).isEqualTo(-1);
            softly.assertThat(atConcertDate.quality).isEqualTo(0);

            softly.assertThat(afterConcertDate.sellIn).isEqualTo(-2);
            softly.assertThat(afterConcertDate.quality).isEqualTo(0);

            softly.assertThat(farAfterConcertDate.sellIn).isEqualTo(-8);
            softly.assertThat(farAfterConcertDate.quality).isEqualTo(0);

            softly.assertThat(maxQuality.sellIn).isEqualTo(6);
            softly.assertThat(maxQuality.quality).isEqualTo(50);
        });
    }

}
