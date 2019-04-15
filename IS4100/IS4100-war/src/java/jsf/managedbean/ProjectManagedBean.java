/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import ejb.stateless.ProjectEntityControllerLocal;
import ejb.stateless.TaskEntityControllerLocal;
import ejb.stateless.UserEntityControllerLocal;
import entity.ProjectEntity;
import entity.TaskEntity;
import entity.UserEntity;
import exception.UserNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;
import static java.time.temporal.ChronoUnit.DAYS;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedProperty;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Alastair
 */
@Named(value = "projectManagedBean")
@ViewScoped
public class ProjectManagedBean implements Serializable {

    @EJB
    private ProjectEntityControllerLocal projectEntityControllerLocal;
    
    @EJB
    private UserEntityControllerLocal userEntityControllerLocal;
    
    private List<ProjectEntity> projectEntities;
    
    private ProjectEntity newProjectEntity;
    
    private ProjectEntity projectEntityToView;
    
    @ManagedProperty(value="#{param.thisProjId}")
    private ProjectEntity projectEntityManaged;
   
    
    @EJB
    private TaskEntityControllerLocal taskEntityControllerLocal;
    
    private TaskEntity taskEntityToCreate;
    
    private TaskEntity taskEntityToView;
    private TaskEntity taskEntityToUpdate;
    private Long taskProjId;
    
    private List<Date> multi;
    private List<Date> range;
    private List<Date> invalidDates;
    private List<Integer> invalidDays;
    private Date minDate;
    private Date maxDate;
    private Long userId;
    
    private Long projectId;

    
    
    public ProjectManagedBean() {
        newProjectEntity = new ProjectEntity();
        projectEntityToView = new ProjectEntity();
        taskEntityToView = new TaskEntity();
        System.out.println("HI");
        invalidDates = new ArrayList<>();
        Date today = new Date();
        invalidDates.add(today);
        long oneDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 5; i++) {
            invalidDates.add(new Date(invalidDates.get(i).getTime() + oneDay));
        }
 
        invalidDays = new ArrayList<>();
        invalidDays.add(0); /* the first day of week is disabled */
        invalidDays.add(3);
 
        minDate = new Date(today.getTime() - (365 * oneDay));
        maxDate = new Date(today.getTime() + (365 * oneDay));
        taskEntityToCreate = new TaskEntity();
        projectId = null;
    }
    
    @PostConstruct
    public void postConstruct()
    {
        try {
            //projectId = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("thisProjId");
            //projectEntityToView = projectEntityControllerLocal.retrieveProjectByProjectId(projectId);
            System.out.println("long.value of " +projectId);
            projectId = Long.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("projectId"));
            
            System.out.println("long.value of " +projectId);
            projectEntityToView = projectEntityControllerLocal.retrieveProjectByProjectId(projectId);
        } catch(Exception ex) {
            
        }
    }
 
    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }
 
    public void click() {
        PrimeFaces.current().ajax().update("form:display");
        PrimeFaces.current().executeScript("PF('dlg').show()");
    }
    
    public void createNewProject(ActionEvent event) throws IOException  {
        System.out.println("HI123");
        UserEntity ue1 = (UserEntity) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentUserEntity");
        
        System.out.println("userID"+ ue1.getUserId()+ue1.getUsername());
        UserEntity ue = userEntityControllerLocal.retrieveUserByUserId(ue1.getUserId());
        
        
        
        newProjectEntity.setUserEntity(ue);
        int numOfDaysInBetween = (int)((newProjectEntity.getEndDate().getTime() - newProjectEntity.getStartDate().getTime())/(1000*60*60*24));
        System.out.println("numOfDaysInBetween" + numOfDaysInBetween);
        newProjectEntity.setTotalDays(numOfDaysInBetween);
        int dayPassed = (int)((newProjectEntity.getEndDate().getTime() - newProjectEntity.getCurrentDate().getTime())/(1000*60*60*24)); 
        newProjectEntity.setDaysPassed(dayPassed);
        
        newProjectEntity.setProjectedBudgetAmount(newProjectEntity.getBudget());
        ProjectEntity pe = projectEntityControllerLocal.createNewProject(newProjectEntity);
        
        newProjectEntity = new ProjectEntity();
        
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New project created successfully (Project ID: " + pe.getProjectId() + ")", null));
        FacesContext.getCurrentInstance().getExternalContext().redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + "/dashboard.xhtml");
    }
    
    public void viewCurrProject(ActionEvent event) {
        
        
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();

        String abcd = request.getPathInfo();
        System.out.println(abcd);
    }
    
    public String navigate(String page, Long id) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("thisProjId", id);
        String abcd = request.getParameter("projectEntityToView");
        System.out.println(abcd);
        projectEntityToView = projectEntityControllerLocal.retrieveProjectByProjectId(id);
        System.out.println("proj" + projectEntityToView.getProjectName());
        return page + "?faces-redirect=true&id=" + id;
    }
    
    
    public void createNewTask(ActionEvent event) {
        
        
        //System.out.println("userID"+ ue1.getProjectName()+ue1.getProjectId());
        System.out.println("id" + projectEntityToView.getProjectId());
        taskEntityToCreate.setCompleted(false);
        taskEntityToCreate.setSpent(BigDecimal.ZERO);
        
        double percentage = taskEntityToCreate.getPoints()/projectEntityToView.getTotalPoints();
        
        BigDecimal p = new BigDecimal(percentage, MathContext.DECIMAL64);
        MathContext m = new MathContext(2);
        BigDecimal taskB = projectEntityToView.getBudget().multiply(p).round(m);
        
        taskEntityToCreate.setTaskBudget(taskB);
        
        TaskEntity te = taskEntityControllerLocal.createNewTask(taskEntityToCreate,projectEntityToView.getProjectId());
    }
    
    
    public void doUpdateTask(ActionEvent event)
    {
        taskEntityToUpdate = (TaskEntity)event.getComponent().getAttributes().get("taskEntityToUpdate");
        
        taskProjId = taskEntityToUpdate.getProjectEntity().getProjectId();
        
    }
    
    public void updateTask(ActionEvent event) {
        try {
            double percentage = taskEntityToUpdate.getPoints()/projectEntityToView.getTotalPoints();
            BigDecimal p = new BigDecimal(percentage, MathContext.DECIMAL64);
            BigDecimal taskB = projectEntityToView.getBudget().multiply(p);
        
            taskEntityToUpdate.setTaskBudget(taskB);
            
//            double percentageCompleted = taskEntityToUpdate.getPointsCompleted()/projectEntityToView.getTotalPoints();
//            BigDecimal p1 = new BigDecimal(percentageCompleted, MathContext.DECIMAL64);
//            BigDecimal taskBudgetCompleted = projectEntityToView.getBudget().multiply(p1);
//            
//            taskEntityToUpdate.setSpent(taskBudgetCompleted);
            
            taskEntityControllerLocal.updateTask(taskEntityToUpdate, taskProjId, false);
        } catch (Exception ex) {
            
        }
    }
    
    public TaskEntity getTaskEntityToUpdate() {
        return taskEntityToUpdate;
    }

    public void setTaskEntityToUpdate(TaskEntity taskEntityToUpdate) {
        this.taskEntityToUpdate = taskEntityToUpdate;
    }

    

    public TaskEntity getTaskEntityToCreate() {
        return taskEntityToCreate;
    }

    public void setTaskEntityToCreate(TaskEntity taskEntityToCreate) {
        this.taskEntityToCreate = taskEntityToCreate;
    }
    

    public ProjectEntityControllerLocal getProjectEntityControllerLocal() {
        return projectEntityControllerLocal;
    }

    public ProjectEntity getProjectEntityToView() {
        return projectEntityToView;
    }

    public void setProjectEntityToView(ProjectEntity projectEntityToView) {
        this.projectEntityToView = projectEntityToView;
    }

    public void setProjectEntityControllerLocal(ProjectEntityControllerLocal projectEntityControllerLocal) {
        this.projectEntityControllerLocal = projectEntityControllerLocal;
    }

    public ProjectEntity getProjectEntityManaged() {
        return projectEntityManaged;
    }

    public void setProjectEntityManaged(ProjectEntity projectEntityManaged) {
        this.projectEntityManaged = projectEntityManaged;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    

    public List<Date> getMulti() {
        return multi;
    }

    public void setMulti(List<Date> multi) {
        this.multi = multi;
    }

    public List<Date> getRange() {
        return range;
    }

    public void setRange(List<Date> range) {
        this.range = range;
    }

    public List<Date> getInvalidDates() {
        return invalidDates;
    }

    public void setInvalidDates(List<Date> invalidDates) {
        this.invalidDates = invalidDates;
    }

    public List<Integer> getInvalidDays() {
        return invalidDays;
    }

    public void setInvalidDays(List<Integer> invalidDays) {
        this.invalidDays = invalidDays;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public ProjectEntity getNewProjectEntity() {
        return newProjectEntity;
    }

    public void setNewProjectEntity(ProjectEntity newProjectEntity) {
        this.newProjectEntity = newProjectEntity;
    }

    

    
    
    public List<ProjectEntity> retrieveListOfProjects(Long id) {
        projectEntities = projectEntityControllerLocal.retrieveProjectByUserId(id);
        return projectEntities;
    }

    public List<ProjectEntity> getProjectEntities() {
        return projectEntities;
    }

    public void setProjectEntities(List<ProjectEntity> projectEntities) {
        this.projectEntities = projectEntities;
    }
    
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public TaskEntity getTaskEntityToView() {
        return taskEntityToView;
    }

    public void setTaskEntityToView(TaskEntity taskEntityToView) {
        this.taskEntityToView = taskEntityToView;
    }
    
    
    
}
