package fleacircus.com.learningproject;

public class Questions {


    public String questions[] = {
            "Which is not used in Networking",
            "Which is a Programming Language?",
            "Which is an incorrect AS name",
            "Which protocol does DHCP use at the Transport layer",
            "How long is an IPv6 address"
    };

    public String choices[][] = {
            {"RIP", "TCP", "TAP", "EIGRP"},
            {"HTML", "CSS", "Vala", "PHP"},
            {"AS600", "500AS", "AS100", "AS200"},
            {"UDP", "TCP", "ARP", "IP"},
            {"32 bits", "128 bytes", "64 bits", "128 bits"}
    };

    public String correctAnswer[] = {
            "TAP",
            "PHP",
            "500AS",
            "UDP",
            "128 bits"
    };

    public String getQuestion(int a){
        String question = questions[a];
        return question;
    }

    public String getchoice1(int a){
        String choice = choices[a][0];
        return choice;
    }

    public String getchoice2(int a){
        String choice = choices[a][1];
        return choice;
    }

    public String getchoice3(int a){
        String choice = choices[a][2];
        return choice;
    }

    public String getchoice4(int a){
        String choice = choices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){
        String answer = correctAnswer[a];
        return answer;
    }
}
