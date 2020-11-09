
package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.Entry;

import java.util.List;

public class CircleBuffer extends AbstractBuffer<Entry> {

    public CircleBuffer(int size) {
        super(size);
    }

    @Override
    public void feed(Entry e) {
        int size = (int)Math.ceil((mTo - mFrom) * phaseX + mFrom);

        for (int i = mFrom; i < size; i++) {

            addCircle(e.getXIndex(), e.getVal() * phaseY);
        }

        reset();
    }

    protected void addCircle(float x, float y) {
        buffer[index++] = x;
        buffer[index++] = y;
    }



}
