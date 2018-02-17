package program.unit;

import program.Task;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class TaskGenerator extends Unit{
    private int num;
    private int minCompl;
    private int maxCompl;
    private int taskHappenProbability;
//    public static final double procProbability ;

    public TaskGenerator(int minCompl, int maxCompl, int taskHappenProbability) {
        super(1);
        this.minCompl = minCompl;
        this.maxCompl = maxCompl;
        this.taskHappenProbability = taskHappenProbability;

        System.out.println("TASK GENERATOR{ MIN = "+minCompl+", MAX = "+maxCompl+" }");
    }

    @Override
    protected void tick(double step) {
        if(new Random().nextInt(100) >= taskHappenProbability)
            return;

        int complexity = generateComplexity();
        Set<Integer> procesors = generateProcessors();
        Task task = new Task(complexity, procesors);
        this.getSystem().tasks.add(task);
        System.out.println("Task generated, complexity:"+complexity+",processors: "+procesors+" ------------ || "+(++num));
        this.getSystem().taskAmount++;
    }

    private Set<Integer> generateProcessors() {
        HashSet<Integer> result = new HashSet<>();
        int numOfProc = this.getSystem().getProcessors().size();
        for (int i = 0; i < numOfProc; i++){
            if (new Random().nextBoolean())
                result.add(i);
        }
        if (result.isEmpty())
            result.add(new Random().nextInt(numOfProc));
        return result;
    }

    private int generateComplexity() {
        return ThreadLocalRandom.current().nextInt(minCompl, maxCompl + 1);
       // return  minCompl + new Random().nextInt(maxCompl+1); // [low..upp];
    }

}