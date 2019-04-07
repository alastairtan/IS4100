/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Alastair
 */
@Entity
public class TaskEntity implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    
    private boolean completed;
    
    private BigDecimal taskBudget;
    
    private BigDecimal spent;
    
    private double points;
    
    private double pointsCompleted;

    private boolean isOverBudget;
    private String name;
    
    @ManyToOne
    private ProjectEntity projectEntity;

    public TaskEntity() {
        taskBudget = BigDecimal.ZERO;
        spent = BigDecimal.ZERO;
        pointsCompleted = 0;
        points = 0;
        isOverBudget = false;
        
    }

    public boolean isIsOverBudget() {
        return isOverBudget;
    }

    public void setIsOverBudget(boolean isOverBudget) {
        this.isOverBudget = isOverBudget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public BigDecimal getTaskBudget() {
        return taskBudget;
    }

    public void setTaskBudget(BigDecimal taskBudget) {
        this.taskBudget = taskBudget;
    }

    public BigDecimal getSpent() {
        return spent;
    }

    public void setSpent(BigDecimal spent) {
        this.spent = spent;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }
    
    
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public double getPointsCompleted() {
        return pointsCompleted;
    }

    public void setPointsCompleted(double pointsCompleted) {
        this.pointsCompleted = pointsCompleted;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskId != null ? taskId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the taskId fields are not set
        if (!(object instanceof TaskEntity)) {
            return false;
        }
        TaskEntity other = (TaskEntity) object;
        if ((this.taskId == null && other.taskId != null) || (this.taskId != null && !this.taskId.equals(other.taskId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TaskEntity[ id=" + taskId + " ]";
    }
    
}
