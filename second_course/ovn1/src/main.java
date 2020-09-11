import se.lth.cs.pt.window.SimpleWindow;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class main {

    public static void main(String[] args) {
        Machine[] m = new Machine[3];
        for (int i = 0; i < m.length; i++) {
            m[i] = new Machine(i + 1);
        }

        ArrayList<Job> jobList = new ArrayList<Job>();
        String [] names = {"j4", "j2", "j5", "j6", "j3", "j7", "j1"};
        int[] times = {16, 14, 6, 5, 4, 3, 2};
        for (int i = 0; i < names.length; i++) {
            jobList.add(new Job(names[i], times[i]));
        }

        Scheduler s = new Scheduler(m);
        s.makeSchedule(jobList);
        s.printSchedule();
    }

}

class Job {
    private String name;
    private int time;

    public Job(String name, int time) {
        this.name = name;
        this.time = time;
    }
    int getTime() {
        return this.time;
    }
    public String toString() {
        return String.format("%s (%d)", this.name, this.time).toString();
    }
}

class Machine {
    private int nbr;
    private Queue<Job> jobs;

    public Machine(int nbr) {
        this.nbr = nbr;
        this.jobs = new LinkedList<Job>();
    }

    int getNbr() {
        return this.nbr;
     }

    void assignJob(Job j) {
        this.jobs.add(j);
    }

    public void clearJobs() {
        this.jobs.clear();
    }

    Job getNextJob() {
        return (Job) this.jobs.remove();
    }

    public int getTotalTime() {
        AtomicInteger time = new AtomicInteger(0);
        this.jobs.forEach((job) -> time.addAndGet(job.getTime()));
        return time.get();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Job job: this.jobs) {
            sb.append(String.format("[%s], ", job));
        }
        String jobs = sb.length() > 2 ? sb.substring(0, sb.length() - 2) : sb.toString();
        return String.format("%s %s", this.nbr, jobs);
    }
}

class Scheduler {

    private Machine[] machines;
    public Scheduler(Machine[] machines) {
        this.machines = machines;
    }

    void makeSchedule(ArrayList<Job> jobs) throws ArrayIndexOutOfBoundsException {
        int currMachine = 0, currJob = 0;

        Machine nextMachine = this.machines[currMachine++];

        while (!jobs.isEmpty()) {
            nextMachine = this.getNextMachine();
            nextMachine.assignJob(jobs.get(currJob));
            jobs.remove(currJob);
        }
    }

    private Machine getNextMachine() throws ArrayIndexOutOfBoundsException {
        if (this.machines.length == 0) {
            throw new ArrayIndexOutOfBoundsException("Machines not initialized yet");
        }
        Machine next = this.machines[0];
        for (Machine m: this.machines) {
            if (m.getTotalTime() < next.getTotalTime() &&
                m != next) {
                next = m;
            }
        }
        return next;
    }

    public void printSchedule() {
        for (Machine machine: this.machines) {
            System.out.println(machine.toString());
        }
    }

}
