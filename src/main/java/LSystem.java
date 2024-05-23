import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LSystem {

    private String axiom;
    private float angle;
    private Map<Character, String> rules;
    private int iterations;

    public LSystem(String axiom, float angle, Map<Character, String> rules) {
        this.axiom = axiom;
        this.angle = angle;
        this.rules = rules;
    }

    public String applyRule(String c) {
        String r = rules.get(c.charAt(0));
        if (r != null) {
            return r;
        } else {
            return c;
        }
    }

    public String applyRule(String a, int iteration) {
        String s = a;
        for (int k = 0; k < iteration; k++) {
            String new_string = "";
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                String r = rules.get(c);
                if (r != null) {
                    new_string += r;
                } else {
                    new_string += c;
                }
            }
            s = new_string;
            if (s.equals(a)) {
                break;
            }
        }
        return s;
    }

    public String getFractal() {
        return applyRule(axiom, iterations);
    }

    public String getAxiom() {
        return axiom;
    }

    public float getAngle() {
        return angle;
    }

    public void setIteration(int i) {
        iterations = i;
    }



    public String getFractalParallel() throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        // para no crear hebras inecesarias.
        if (numThreads > axiom.length()) {
            numThreads = axiom.length();
        }
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<String>> futures = new ArrayList<>();

        // si hay menos se va a quedar como 0 y no se divide nada

        int partSize = (int) Math.ceil((double) axiom.length() / numThreads);
        int start = 0;
        for (int i = 0; i < numThreads; i++) {

            if (start == axiom.length()) {
                break;
            }
            int end = (i == numThreads - 1) ? axiom.length() : (start + partSize);
            int s = start;
            Callable<String> task = () -> applyRule(axiom.substring(s, end), iterations);
            futures.add(executor.submit(task));
            start = end;
        }

        StringBuilder resultBuilder = new StringBuilder();
        for (Future<String> future : futures) {
            resultBuilder.append(future.get());
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return resultBuilder.toString();
    }

    // paralelizar por iteraciones para asignar todas las hebras el mayor tiempo
    // posible
    public String getFractalParallel2() throws InterruptedException, ExecutionException {

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<String>> futures = new ArrayList<>();

        String string = axiom;
        int iter=0;

        if (string.length() < numThreads) {
            string = applyRule(string);
            iter=1;
        }

        for (int i = 0; i < string.length(); i++) {
            int totaliter=iterations-iter;
            String substring = string.substring(i, i + 1);
            Callable<String> task = () -> applyRule(substring, totaliter);
            futures.add(executor.submit(task));

        }

        StringBuilder resultBuilder = new StringBuilder();
        for (Future<String> future : futures) {
            resultBuilder.append(future.get());
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        return resultBuilder.toString();
    }

    public String getFractalParallelIter() throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    
        String string = axiom;
        for (int iter = 0; iter < iterations; iter++) {
            List<Future<String>> futures = new ArrayList<>();


            //por cada iteracion
            for (int i = 0; i < string.length(); i++) {
                String character = string.substring(i, i + 1);
                //aplicar la regla a cada caracter 
                Callable<String> task = () -> applyRule(character,1);
                futures.add(executor.submit(task));
            }
    
            StringBuilder nextStringBuilder = new StringBuilder();
            for (Future<String> future : futures) {
                nextStringBuilder.append(future.get());
            }
    
            string = nextStringBuilder.toString();
        }
    
        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    
        return string;
    }
    

}