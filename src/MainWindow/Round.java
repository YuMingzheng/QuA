package MainWindow;


/**
 * @author Yu Mingzheng
 * @date 2022/10/19 18:38
 * @apiNote
 */
public class Round {
    /**
     * Round -> (UserID,Panel,Round,roundStartCur,roundEndCur,TransTimes,transWordNum,ClickAns,TrueAns)
     *          (用户ID，面板， 轮数， 开始时刻 ，    结束时刻    ，翻译次数，   翻译单词数量， 选择的答案，正确答案)
     */
    private String userID;
    private int panel;
    private int round;
    private long roundStartCur;
    private long roundEndCur;
    private int transTimes;
    private int transWordNum;
    private String clickAns;
    private String trueAns;

    public Round(String userID,int panel , int round) {
        this.userID = userID;
        this.panel = panel;
        this.round = round;
    }


    public Round(String userID, int panel, int round, long roundStartCur, long roundEndCur, int transTimes, String clickAns, String trueAns) {
        this.userID = userID;
        this.panel = panel;
        this.round = round;
        this.roundStartCur = roundStartCur;
        this.roundEndCur = roundEndCur;
        this.transTimes = transTimes;
        this.clickAns = clickAns;
        this.trueAns = trueAns;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public int getPanel() {
        return panel;
    }

    public void setPanel(int panel) {
        this.panel = panel;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public long getRoundStartCur() {
        return roundStartCur;
    }

    public void setRoundStartCur(long roundStartCur) {
        this.roundStartCur = roundStartCur;
    }

    public long getRoundEndCur() {
        return roundEndCur;
    }

    public void setRoundEndCur(long roundEndCur) {
        this.roundEndCur = roundEndCur;
    }

    public int getTransTimes() {
        return transTimes;
    }

    public void setTransTimes(int transTimes) {
        this.transTimes = transTimes;
    }

    public int getTransWordNum() {
        return transWordNum;
    }

    public void setTransWordNum(int transWordNum) {
        this.transWordNum = transWordNum;
    }

    public String getClickAns() {
        return clickAns;
    }

    public void setClickAns(String clickAns) {
        this.clickAns = clickAns;
    }

    public String getTrueAns() {
        return trueAns;
    }

    public void setTrueAns(String trueAns) {
        this.trueAns = trueAns;
    }

    public String[] toStringList(){
        String[] s = new String[9];

        s[0] = userID;
        s[1] = String.valueOf(panel);
        s[2] = String.valueOf(round);
        s[3] = String.valueOf(roundStartCur);
        s[4] = String.valueOf(roundEndCur);
        s[5] = String.valueOf(transTimes);
        s[6] = String.valueOf(transWordNum);
        s[7] = clickAns;
        s[8] = trueAns;

        return s;
    }

    @Override
    public String toString(){

        return userID+", "+
                panel+", "+
                round+", "+
                roundStartCur+", "+
                roundEndCur+", "+
                transTimes+", "+
                transWordNum+", "+
                clickAns+", "+
                trueAns
                ;

    }

    public static void main(String[] args) {

        Round round  = new Round("1",1 , 1);

        System.out.println(round.toString());


    }
}
