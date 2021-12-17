package com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.annotator;

import com.pubmedknowledgegraph.worker.pubmedknowledgegraph_worker.model.util.MMITag;

import java.util.ArrayList;
import java.util.List;

public class Annotate {

    private final Annotator annotator;

    public Annotate(Annotator annotator) {
        this.annotator = annotator;
    }

    public List<MMITag> annotate(String text) {
        try {
            return runAnnotator(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private List<MMITag> runAnnotator(String text) throws Exception {
        List<MMITag> mmiTagList = new ArrayList<>();
        String[] mmiTagTitle = getTagFromText(text);
        for (String mmiTag : mmiTagTitle) {
            if (!mmiTag.equals("")) {
                mmiTagList.add(buildTag(mmiTag));
            }
        }
        return mmiTagList;
    }

    private MMITag buildTag(String mmiTag) {
        String[] tagField = mmiTag.trim().split("\\|");
        MMITag mmiTagObj = new MMITag(tagField[2], tagField[1], tagField[3], tagField[0]);
        mmiTagObj.setTriggerInfo(tagField[4]);
        mmiTagObj.setPositionalInfo(tagField[6]);
        if (tagField.length > 7) {
            mmiTagObj.setTreeCodes(tagField[7]);
        }
        return mmiTagObj;
    }

    private String[] getTagFromText(String text) throws Exception {
        return annotator.getTag(text);
    }
}
