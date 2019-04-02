/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.TaskEntityControllerLocal;
import entity.ProjectEntity;
import entity.TaskEntity;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Alastair
 */
@Named(value = "taskManagedBean")
@ViewScoped
public class TaskManagedBean implements Serializable{

    @EJB
    private TaskEntityControllerLocal taskEntityControllerLocal;
    
    private TaskEntity taskEntityToCreate;
    
    private ProjectEntity projectEntity;
    
    public TaskManagedBean() {
        taskEntityToCreate = new TaskEntity();
        projectEntity = new ProjectEntity();
    }

    public TaskEntity getTaskEntityToCreate() {
        return taskEntityToCreate;
    }

    public void setTaskEntityToCreate(TaskEntity taskEntityToCreate) {
        this.taskEntityToCreate = taskEntityToCreate;
    }
    
    public void createNewTask(ActionEvent event) {
        ProjectEntity ue1 = (ProjectEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentProjectEntity");
        System.out.println("projectEntity" + projectEntity.getProjectName());
        //System.out.println("userID"+ ue1.getProjectName()+ue1.getProjectId());
        taskEntityToCreate.setProjectEntity(ue1);
        taskEntityToCreate.setCompleted(false);
        taskEntityToCreate.setSpent(BigDecimal.ZERO);
        //TaskEntity te = taskEntityControllerLocal.createNewTask(taskEntityToCreate,);
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }
    
    
}
