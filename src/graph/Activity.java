package graph;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Activity {

	public static AtomicInteger uniqueID = new AtomicInteger();
    private int id;
    private String name;
    String dependencies;
    private int duration;
    private int   esDays, efDays, lsDays, lfDays, slack;
    private List<Activity> predecessors , successors;

    // Clone constructor
    public Activity(Activity activity) {
        this();
        setActivity(activity);
    }

    public Activity() {
        this(uniqueID.getAndIncrement(), "New_Activity", 1 );
    }
    

    public Activity(int id) {
        this(id, "New_Activity", 1);
    }

    public Activity(int id, String name, int duration) {
        this.id = id;
        this.name = name;
        this.dependencies = "-";
        this.duration = duration;  
        this.esDays = 0;
        this.efDays = getEsDays() + duration;
        this.lfDays = getEfDays();
        this.lsDays = getEfDays() - duration;
        this.slack = getEsDays() - getEsDays();
        this.predecessors = new ArrayList<Activity>();
        this.setSuccessors(new ArrayList<Activity>());
    }


    public void commitDuration(int dur) {
        if (dur > 0) {
            duration = dur;
        }
    }

    // Setters - Getters
    public void setActivity(Activity newActivity) {
        setId(newActivity.getId());
        setName(newActivity.getName());
        setDependencies(newActivity.getDependencies());
        setDuration(newActivity.getDuration());
        setEsDays(newActivity.getEsDays());
        setLsDays(newActivity.getLsDays());
        setEfDays(newActivity.getEfDays());
        setLfDays(newActivity.getLfDays());
        setPredecessors(newActivity.getPredecessors());
        setSuccessors(newActivity.getSuccessors());
    }

    public int getId() {
        return id;
    }

    

    public void setId(int id) {
        this.id=id;
    }

    public String getName() {
        return name;
    }

    

    public void setName(String name) {
        this.name = name;
    }

    
	public int getDuration() {
        return duration;
    }

    

    public void setDuration(int duration) {
        this.duration = duration;
    }
 
    public List<Activity> getPredecessors() {
        return predecessors;
    }

    public void setPredecessors(List<Activity> list) {
        this.predecessors = list;
    }


    public String getDependencies() {
        return dependencies;
    }


    public void setDependencies(String dependencies) {
        this.dependencies = dependencies;
    }

   

    public int getSlack() {
        return slack;
    }

   

    public void setSlack(int slack) {
        this.slack=slack;
    }

	public List<Activity> getSuccessors() {
		return successors;
	}

	public void setSuccessors(List<Activity> successors) {
		this.successors = successors;
	}

	public int getEsDays() {
		return esDays;
	}

	public void setEsDays(int esDays) {
		this.esDays = esDays;
	}

	public int getEfDays() {
		return efDays;
	}

	public void setEfDays(int efDays) {
		this.efDays = efDays;
	}

	public int getLsDays() {
		return lsDays;
	}

	public void setLsDays(int lsDays) {
		this.lsDays = lsDays;
	}

	public int getLfDays() {
		return lfDays;
	}

	public void setLfDays(int lfDays) {
		this.lfDays = lfDays;
	}

	@Override
	public String toString() {
		return "Activity " + name + " [ duration=" + duration + ", date plus tot=" + esDays + ", date plus tard=" + lsDays+ "]";
	}
	
	public void setSus(List<Activity> s) {
		for (Activity a : s) {
			//if a in s pres then s in a suc*
			if(a.getPredecessors().contains(this)) {
				this.successors.add(a);
			}
			
            
        }
	}
	
	
	public String getCritical() {
		
		return " [ name=" + name + ", duration=" + duration+ "]" + ((this.getSuccessors().isEmpty())? "":"=>");
		
	}
}
