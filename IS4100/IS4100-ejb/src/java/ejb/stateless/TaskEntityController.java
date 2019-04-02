/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.ProjectEntity;
import entity.TaskEntity;
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
    public void updateTask(TaskEntity taskEntity) {
        if(taskEntity != null) {
            TaskEntity taskEntityToUpdate = retrieveTaskByTaskId(taskEntity.getTaskId());
            
            if(taskEntityToUpdate.getTaskId().equals(taskEntity.getTaskId()))
                {
                    taskEntityToUpdate.setCompleted(taskEntity.isCompleted());
                    taskEntityToUpdate.setTaskBudget(taskEntity.getTaskBudget());
                    taskEntityToUpdate.setSpent(taskEntity.getSpent());
                    taskEntityToUpdate.setPoints(taskEntity.getPoints());
                    
                    
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
