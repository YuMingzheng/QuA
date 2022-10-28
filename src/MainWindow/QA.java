package MainWindow;


/**
 * @author Yu Mingzheng
 * @date 2022/10/18 16:50
 * @apiNote
 */
public class QA {
    private String question;
    private String[] answers;
    private String trueAns;

    public QA(String question , String[] anss , String trueAns){
        this.question = question;
        this.answers = anss;
        this.trueAns = trueAns;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public String getTrueAns() {
        return trueAns;
    }

    public void setTrueAns(String trueAns) {
        this.trueAns = trueAns;
    }
}
