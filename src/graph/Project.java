package graph;


import java.util.ArrayList;
import java.util.List;


public class Project {


	private List<Activity> criticalActivities;
	private List<Activity> criticalPaths;
	public List<Activity> activities;
	private int projectEarliestFinish;
	
	public Project() {
		super();
		this.criticalActivities = new ArrayList<Activity>();
		this.criticalPaths = new ArrayList<Activity>();
		this.activities = new ArrayList<Activity>();
	}
	public List<Activity> getCriticalPaths() {
		return criticalPaths;
	}
	public String getCriticalStiring() {
		String result = "critical path \n";
		for(Activity a: criticalPaths) {
			if(!a.getPredecessors().isEmpty()) {
				result +=a.getCritical();
			}
			
		}
		return result;
	}

	public void updateStats() {
        for (Activity a : activities) {
            if (a.getPredecessors().isEmpty()) {
                a.setEsDays(0);
                a.setEfDays(a.getEsDays() + a.getDuration());
                forwardPass(a);
            }
        }
        projectEarliestFinish= maxEFLeafs(activities);
        for (Activity a : activities) {
            if (a.getSuccessors().isEmpty()) {
                a.setLfDays(projectEarliestFinish);
                a.setLsDays(a.getLfDays() - a.getDuration());
                a.setSlack(a.getLsDays() - a.getEsDays());
                backwardPass(a);
            }
        }
        extractCriticalPaths();
       
        
    }
	

	public String maxEndDate() {
		int sum = 0;
		for(Activity a : getCriticalPaths()) {
			sum +=a.getDuration();
			}
		return "la dure minimal du project est :"+sum;
	}
	private void forwardPass(Activity root) {
		 for (Activity a : root.getSuccessors()) {
	            a.setEsDays(maxEarliestFinish(a.getPredecessors()));
	            a.setEfDays(a.getEsDays() + a.getDuration());
	            forwardPass(a);
	        }
    }

    private int maxEarliestFinish(List<Activity> predecessors) {
        int max = 0;
        for (Activity a : predecessors) {
            if (a.getEfDays() > max) {
                max = a.getEfDays();
            }
        }
        return max;
	}


	// Second pass calculating LF, LS and Slack
    private void backwardPass(Activity root) {
    	 for (Activity a : root.getPredecessors()) {
             a.setLfDays(minLatestStart(a.getSuccessors()));
             a.setLsDays(a.getLfDays() - a.getDuration());
             a.setSlack(a.getLsDays() - a.getEsDays());
             backwardPass(a);
         }
    }


	private int minLatestStart(List<Activity> successors) {
		if (successors.isEmpty()) {
            return 0;
        }
        int min = successors.get(0).getLsDays();
        for (int i = 1; i < successors.size(); i++) {
            if (successors.get(i).getLsDays() < min) {
                min = successors.get(i).getLsDays();
            }
        }
        return min;
	}
	

    void extractCriticalPaths() {
        criticalActivities.clear();
        criticalPaths.clear();
        for (Activity a : activities) {
            if (a.getSlack() == 0) {
                criticalActivities.add(a);
            }
        }
        for (Activity a : criticalActivities) {
            if (a.getPredecessors().isEmpty()) {
                ArrayList<Activity> list = new ArrayList<Activity>();
                extractCriticalPaths(a, list);
            }
        }
    }

    private void extractCriticalPaths(Activity root, List<Activity> list) {
        list.add(root);
        if (root.getSuccessors().isEmpty()) {
            criticalPaths.addAll(list);
        } else {
            for (Activity a : root.getSuccessors()) {
                if (a.getSlack() == 0) {
                    extractCriticalPaths(a, list);
                }
            }
        }
    }
    
    public Activity getActivityByName(String name) {
    	for (Activity a : activities) {
            if (a.getName().equals(name) ) {
                return a;
            }
        }
    	return null;
    }
    
    public static int maxEFLeafs(List<Activity> activities) {
        int maxEarliestFinish = 0;
        for (Activity a : activities) {
            if (a.getSuccessors().isEmpty()) {
                if (a.getEfDays() > maxEarliestFinish) {
                    maxEarliestFinish = a.getEfDays();
                }
            }
        }
        return maxEarliestFinish;
    }

}
