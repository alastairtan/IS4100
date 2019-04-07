/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.ProjectEntity;
import entity.TaskEntity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alastair
 */
@Stateless
public class TaskEntityController implements TaskEntityControllerLocal {

    @PersistenceContext(unitName = "IS4100-ejbPU")
    private EntityManager em;
    
    @EJB
    private ProjectEntityControllerLocal projectEntityControllerLocal;

    @Override
    public TaskEntity createNewTask(TaskEntity taskEntity, Long projectId) {
        
        ProjectEntity pe = projectEntityControllerLocal.retrieveProjectByProjectId(projectId);
        
        taskEntity.setProjectEntity(pe);
        em.persist(taskEntity);
        em.flush();
        pe.getTaskEntitys().add(taskEntity);
        em.persist(pe);
        em.flush();
        if(!pe.isIsInitial()) {
            updateTask(taskEntity, projectId,true);
        }
        
        return taskEntity;
    }
    
    @Override
    public List<TaskEntity> retrieveTaskEntitiesByProjectId(Long id) {
        Query query = em.createQuery("SELECT s FROM TaskEntity s WHERE s.projectEntity.projectId =  :inProjectId");
        query.setParameter("inProjectId", id);
        
        return query.getResultList();
    }
    
    @Override
    public TaskEntity retrieveTaskByTaskId(Long id) {
        TaskEntity taskEntity = em.find(TaskEntity.class, id);
        if(taskEntity == null) {
            return null;
        }
        return taskEntity;
    }
    
    @Override
    public void updateTask(TaskEntity taskEntity, Long projectId, boolean create) {
        if(taskEntity != null) {
            TaskEntity taskEntityToUpdate = retrieveTaskByTaskId(taskEntity.getTaskId());
            
            if(taskEntityToUpdate.getTaskId().equals(taskEntity.getTaskId()))
                {
                    taskEntityToUpdate.setCompleted(taskEntity.isCompleted());
                    //taskEntityToUpdate.setTaskBudget(taskEntity.getTaskBudget());
                    taskEntityToUpdate.setSpent(taskEntity.getSpent());
                    taskEntityToUpdate.setPoints(taskEntity.getPoints());
                    taskEntityToUpdate.setPointsCompleted(taskEntity.getPointsCompleted());
                    
                    ProjectEntity pe = projectEntityControllerLocal.retrieveProjectByProjectId(projectId);
                    BigDecimal a = BigDecimal.ZERO;
                    double totalPoints = 0;
                    double percentageTotalPoints = 0;
                    
                    double projectTotalPoints = 0;
                    
                    BigDecimal projectedBudget = BigDecimal.ZERO;
                    List<TaskEntity> taskEntitys = pe.getTaskEntitys();
                    
                    for(TaskEntity t : taskEntitys) {
                        if(t.getTaskId().equals(taskEntityToUpdate.getTaskId())) {
                            t = taskEntityToUpdate;
                          
                        }
                        a = a.add(t.getSpent());
                        totalPoints += t.getPointsCompleted();
                        percentageTotalPoints += t.getPointsCompleted();
                        projectTotalPoints += t.getPoints();
                        projectedBudget = projectedBudget.add(t.getTaskBudget());
                        
                    }
                    if(create) {
                        pe.addProjectedBudgetAmount(taskEntity.getTaskBudget());
                    }
                    //pe.setProjectedBudgetAmount(projectedBudget);
                    pe.setTotalPoints(projectTotalPoints);
                    System.out.println(a);
                    System.out.println("percentageTotalPoints" + percentageTotalPoints + " pe.getTotalPoints" + pe.getTotalPoints());
                    double percentageT = (percentageTotalPoints/pe.getTotalPoints()) *100;
                    System.out.println("percentageT= " + percentageT);
                    pe.setPointsPercentageCompleted(percentageT);
                    pe.setCurrentSpent(a);
                    pe.setPointsCompleted(totalPoints);
                    
                    double totalAmtSpent = a.divide(pe.getBudget()).doubleValue() * 100;
                    System.out.println("totalAmtSpent" +totalAmtSpent);
                    pe.setPercentageBudgetSpent(totalAmtSpent);
                    double forSpi = pe.getPointsPercentageCompleted()/totalAmtSpent;
                    pe.setCpi(forSpi);
                    MathContext m = new MathContext(2);
                    BigDecimal taskBudget = taskEntityToUpdate.getTaskBudget().round(m);
                    System.out.println("getSpent = " + taskEntityToUpdate.getSpent() + "getTaskBudget = " + taskBudget);
                    if(taskEntityToUpdate.getSpent().compareTo(taskBudget) == 1 ) {
                        taskEntityToUpdate.setIsOverBudget(true);
                        BigDecimal asdf = taskEntityToUpdate.getSpent().subtract(taskBudget);
                        pe.setProjectedBudgetAmount(pe.getProjectedBudgetAmount().add(asdf));
                        System.out.println("true");
                    } else {
                        taskEntityToUpdate.setIsOverBudget(false);
                    }
                    pe.setCurrentDate(new Date());
                    Date curr = new Date();
                    int dayPassed = (int)((curr.getTime() - pe.getCurrentDate().getTime())/(1000*60*60*24)); 
                    dayPassed = 3; // NEED TO EDIT MANUALLY
                    pe.setDaysPassed(dayPassed);
                    
                    int remainingPoints =(int) (pe.getTotalPoints()-pe.getPointsCompleted());
                    System.out.println("remainingPoints" + remainingPoints);
                    int remainingSprints =  (int)(remainingPoints /(pe.getPointsCompleted()/dayPassed));
                    System.out.println("remainingSprints" + remainingSprints);
                    pe.setRemainingSprints((int) (Math.ceil(remainingSprints/14.0)) );
                    System.out.println("remainingSprints" + (int) (remainingSprints/7));
                    pe.setIsInitial(false);
                }
        }
    }
    
    @Override
    public void deleteTask(Long taskId) {
        TaskEntity taskEntity = retrieveTaskByTaskId(taskId);
        
        taskEntity.getProjectEntity().getTaskEntitys().remove(taskEntity);
        taskEntity.setProjectEntity(null);
        
        em.remove(taskEntity);
        
        
    }
    
}
