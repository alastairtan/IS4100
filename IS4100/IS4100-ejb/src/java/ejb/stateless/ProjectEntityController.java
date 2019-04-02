/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.ProjectEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Alastair
 */
@Stateless
public class ProjectEntityController implements ProjectEntityControllerLocal {

    @PersistenceContext(unitName = "IS4100-ejbPU")
    private EntityManager em;

    @Override
    public ProjectEntity createNewProject(ProjectEntity projectEntity) {
        em.persist(projectEntity);
        em.flush();
        return projectEntity;
    }
    
    @Override
    public ProjectEntity retrieveProjectByProjectId(Long id) {
        ProjectEntity projectEntity = em.find(ProjectEntity.class, id);
        if(projectEntity == null) {
            return null;
        }
        return projectEntity;
    }
    
    @Override
    public List<ProjectEntity> retrieveProjectByUserId(Long userId) {
        Query query = em.createQuery("SELECT s FROM ProjectEntity s WHERE s.userEntity.userId =  :inUserId");
        query.setParameter("inUserId", userId);
        
        return query.getResultList();
    }
    
    @Override
    public void updateProject(ProjectEntity projectEntity) {
        if(projectEntity != null) {
            ProjectEntity projectEntityToUpdate = retrieveProjectByProjectId(projectEntity.getProjectId());
            
            if(projectEntityToUpdate.getProjectId().equals(projectEntity.getProjectId()))
                {
                    projectEntityToUpdate.setProjectName(projectEntity.getProjectName());
                    projectEntityToUpdate.setStartDate(projectEntity.getStartDate());
                    projectEntityToUpdate.setEndDate(projectEntity.getEndDate());
                    projectEntityToUpdate.setDescription(projectEntity.getDescription());
                    projectEntityToUpdate.setBudget(projectEntity.getBudget());
                    projectEntityToUpdate.setCurrentSpent(projectEntity.getCurrentSpent());
                    
                }
        }
    }

    
}

