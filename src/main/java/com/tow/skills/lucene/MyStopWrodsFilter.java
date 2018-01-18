package com.tow.skills.lucene;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;

import java.io.IOException;

public class MyStopWrodsFilter extends TokenFilter {
    private CharTermAttribute charTermAttribute;
    private PositionIncrementAttribute positionIncrementAttribute;

    public MyStopWrodsFilter(TokenStream input) {
        super(input);
        this.charTermAttribute = charTermAttribute;
        this.positionIncrementAttribute = positionIncrementAttribute;
    }

    @Override
    public boolean incrementToken() throws IOException {
        int extraIncrement = 0;
        boolean returnValue = false;
        while (input.incrementToken()) {
            if (StopAnalyzer.ENGLISH_STOP_WORDS_SET.contains(charTermAttribute.toString())) {
                extraIncrement++;
                continue;
            }
            returnValue = true;
            break;
        }
        if (extraIncrement > 0) {
            positionIncrementAttribute.setPositionIncrement(positionIncrementAttribute.getPositionIncrement() + extraIncrement);
        }
        return returnValue;
    }
}
