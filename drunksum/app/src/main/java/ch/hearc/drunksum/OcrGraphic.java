/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hearc.drunksum;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import ch.hearc.drunksum.camera.GraphicOverlay;
import com.google.android.gms.vision.text.Text;

import java.util.List;

/**
 * Graphic instance for rendering TextBlock position, size, and ID within an associated graphic
 * overlay view.
 */
public class OcrGraphic extends GraphicOverlay.Graphic {

    private int mId;

    private Paint mRectPaint;
    private Paint mTextPaint;
    private final Text mText;
    private double value;

    OcrGraphic(GraphicOverlay overlay, Text text, int color) {
        super(overlay);

        mText = text;

        if (mRectPaint == null) {
            mRectPaint = new Paint();
            mRectPaint.setColor(color);
            mRectPaint.setStyle(Paint.Style.STROKE);
            mRectPaint.setStrokeWidth(4.0f);
        }

        if (mTextPaint == null) {
            mTextPaint = new Paint();
            mTextPaint.setColor(color);
            mTextPaint.setTextSize(54.0f);
        }
        // Redraw the overlay, as this graphic has been added.
        postInvalidate();
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public Text getText() {
        return mText;
    }

    /**
     * Checks whether a point is within the bounding box of this graphic.
     * The provided point should be relative to this graphic's containing overlay.
     * @param x An x parameter in the relative context of the canvas.
     * @param y A y parameter in the relative context of the canvas.
     * @return True if the provided point is contained within this graphic's bounding box.
     */
    public boolean contains(float x, float y) {
        // Check if this graphic's text contains this point.
        if (mText == null) {
            return false;
        }
        RectF rect = new RectF(mText.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        return (rect.left < x && rect.right > x && rect.top < y && rect.bottom > y);
    }

    /**
     * Draws the text block annotations for position, size, and raw value on the supplied canvas.
     */
    @Override
    public void draw(Canvas canvas) {
        // Draw the text onto the canvas.
        if (mText == null) {
            return;
        }

        // Draws the bounding box around the TextBlock.
        RectF rect = new RectF(mText.getBoundingBox());
        rect.left = translateX(rect.left);
        rect.top = translateY(rect.top);
        rect.right = translateX(rect.right);
        rect.bottom = translateY(rect.bottom);
        canvas.drawRect(rect, mRectPaint);

        // Render the text at the bottom of the box.
        canvas.drawText(mText.getValue(), rect.left, rect.bottom, mTextPaint);

        /*
        // Break the text into multiple lines and draw each one according to its own bounding box.
        List<? extends Text> textComponents = mText.getComponents();
        for(Text currentText : textComponents) {
            float left = translateX(currentText.getBoundingBox().left);
            float bottom = translateY(currentText.getBoundingBox().bottom);
            canvas.drawText(currentText.getValue(), left, bottom, mTextPaint);
        }
        */
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getValue(){
        return value;
    }
}
