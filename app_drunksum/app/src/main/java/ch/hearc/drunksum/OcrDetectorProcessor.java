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

import android.graphics.Color;
import android.util.Log;
import android.util.SparseArray;

import ch.hearc.drunksum.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;

import java.util.List;
import java.util.regex.Pattern;

/**
 * A very simple Processor which gets detected TextBlocks and adds them to the overlay
 * as OcrGraphics.
 */
public class OcrDetectorProcessor  implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> mGraphicOverlay;

    // regex pour détecter les nombres, ils ont toujours un point et deux digit après
    // chacun de ces éléments est obligatoire :
    // (\d|O|o)+ un chiffre [0-9], un O ou un o, 1 ou plusieurs fois
    // (\.|\,) un point ou une virgule
    // (\d|o|O){2} un chiffre, un O ou un o, 2 fois
    // dans une string java, il faut échapper les backslahes pour qu'ils soit pris en compte dans la regex
    private static Pattern NUMBER_PATTERN = Pattern.compile("(\\d|o|O)+(\\.|\\,)(\\d|o|O){2}");

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        mGraphicOverlay = ocrGraphicOverlay;
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {
        mGraphicOverlay.clear();

        SparseArray<TextBlock> items = detections.getDetectedItems();
        for (int i = 0; i < items.size(); ++i) {
            // Les textblocks sont des paragraphes,
            // ils contiennent des lignes (Line) qui contienne des mots (Element)
            // TextBlock, Line et Element sont des spécialisation de Text
            // on ne traite que les mots
            // il pourrait être intéressant de faire la regex sur tout le paragraphe avant d'aller plus loin
            // comme ça on ne s'occupe pas des paragraphes qui ne contiennent pas de nombres

            TextBlock block = items.valueAt(i);
            if (block != null && block.getValue() != null) {
                Log.d("Processor", "> paragraphe: " + block.getValue());
                List<? extends Text> lines = block.getComponents();
                for (int j = 0; j < lines.size(); ++j) {
                    Text line = lines.get(j);
                    Log.d("Processor", "-> ligne: '" + line.getValue() + "'");
                    List<? extends Text> words = line.getComponents();
                    for (int k = 0; k < words.size(); k++ ){
                        Text word = words.get(k);
                        Log.d("Processor", "--> mot: '" + word.getValue() + "'");

                        // si le mot est un chiffre, on l'affiche en vert
                        if(NUMBER_PATTERN.matcher(word.getValue()).matches()){
                            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, line, Color.GREEN);
                            mGraphicOverlay.add(graphic);
                        }else{
                            OcrGraphic graphic = new OcrGraphic(mGraphicOverlay, line, Color.WHITE);
                            mGraphicOverlay.add(graphic);
                        }
                    }
                }
            }
        }

    }

    @Override
    public void release() {
        mGraphicOverlay.clear();
    }
}
