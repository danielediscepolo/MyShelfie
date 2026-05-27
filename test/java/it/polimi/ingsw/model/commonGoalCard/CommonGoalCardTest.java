package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.CommonToken;
import junit.framework.Test;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommonGoalCardTest implements Test {

    @org.junit.jupiter.api.Test
    void setCommonTokens() {

        CommonGoalCard commonGoalCard = new C1();


        List<CommonToken> commonTokens = new ArrayList<>();

        commonTokens.add(new CommonToken(8));
        commonTokens.add(new CommonToken(6));
        commonTokens.add(new CommonToken(4));
        commonTokens.add(new CommonToken(2));

        commonGoalCard.setCommonTokens(commonTokens);

        List<CommonToken> commonTokenList = commonGoalCard.getCommonTokens();

        for (CommonToken x: commonTokenList) {
            System.out.println(x.getPoints());
            System.out.println();
        }


    }

    @org.junit.jupiter.api.Test
    void assignCommonTokenAndRemoveFromList() {

        CommonGoalCard commonGoalCard = new C1();

        List<CommonToken> commonTokens = new ArrayList<>();

        commonTokens.add(new CommonToken(8));
        commonTokens.add(new CommonToken(6));
        commonTokens.add(new CommonToken(4));
        commonTokens.add(new CommonToken(2));

        commonGoalCard.setCommonTokens(commonTokens);


        CommonToken commonToken = commonGoalCard.assignCommonTokenAndRemoveFromList();

        System.out.println(commonToken.getPoints());

        CommonToken commonToken1 = commonGoalCard.assignCommonTokenAndRemoveFromList();

        System.out.println(commonToken1.getPoints());


        assertEquals(8,commonToken.getPoints(),"They should be equal");
        assertEquals(6,commonToken1.getPoints(),"They should be equal");


       assertEquals(4,commonGoalCard.getCommonTokens().get(0).getPoints());


    }

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {

    }
}