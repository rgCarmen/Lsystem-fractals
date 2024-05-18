import java.util.Map;

public class LSystem {

    public String axiom;
    public float angle;
    public Map<Character, String> rules;

    public LSystem(String axiom, float angle, Map<Character, String> rules) {
        this.axiom = axiom;
        this.angle = angle;
        this.rules = rules;
    }

    public String applyRule() {
        String new_string = "";
        for (int i = 0; i < axiom.length(); i++) {
            String r=rules.get(axiom.charAt(i));
            if(r!=null){
                new_string += rules.get(axiom.charAt(i));
            }
            else{
                new_string += axiom.charAt(i);
            }
        }
        return new_string;
    }

    public void iterations(int numIter){
        for(int i=0; i<numIter; i++){
            axiom=applyRule();
        }
    }

    public String getAxiom(){
        return axiom;
    }

    public float getAngle(){
        return angle;
    }
}


//recursividad??????????????????????