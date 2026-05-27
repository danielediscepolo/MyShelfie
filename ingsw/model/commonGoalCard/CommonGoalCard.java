package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class CommonGoalCard implements Serializable {
    //Mutable class

    @Serial
    private static final long serialVersionUID = 1L;

    protected  String description;
    public String guiReference;
    private List<CommonToken> commonTokens = new ArrayList<>();

    /**
     * Method which is overridden by the 12 subclasses. It checks whether the turnPlayer has achieved the
     * commonGoalCard. If so, it calls assignCommonTokenAndRemoveFromList, otherwise it returns a new token with a
     * value of 0 points.
     *
     * @param bookshelf of turnPlayer
     * @return a new token: with a score, positive if the common target card has been completed, 0 otherwise.
     */
    public CommonToken compare (Bookshelf bookshelf){
        return new CommonToken();
    }


    public void setCommonTokens(List<CommonToken> commonTokens){

        this.commonTokens = commonTokens;

    }

    /**
     * Method used, in the compare method, by each subclass of CommonGoalCard whenever the turnPlayer has completed the
     * goal of the CommonGoalCard.
     *
     * @return a commonToken. If there is still a commonToken in the commonGoalCard list, it removes the commonToken in
     * the first position and returns an identical one with the same score. The branch else, by design of the game is
     * not reachable, but has been added to ensure robustness.
     */
    public CommonToken assignCommonTokenAndRemoveFromList(){

        if (commonTokens.size() > 0 && commonTokens.get(0) != null) {

            int points = commonTokens.get(0).getPoints();

            CommonToken commonTokenToAssign = new CommonToken(points);

            commonTokens.remove(commonTokens.get(0));

            return commonTokenToAssign;

        }else

            return new CommonToken();

    }


    public String getDescription() {

        return description;
    }


    public List<CommonToken> getCommonTokens() {

        return commonTokens;
    }

    @Override
    public String toString() {
        return "CommonGoalCard{" +
                  description + '\'' +
                '}';
    }

}
