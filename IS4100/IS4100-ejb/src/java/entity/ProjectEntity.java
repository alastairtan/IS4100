/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Alastair
 */
@Entity
public class ProjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    
    private String projectName;
    private Date startDate;
    private Date endDate;
    private String description;
    private BigDecimal budget;
    private BigDecimal currentSpent;
    private double pointsCompleted;
    private double totalPoints;
    private double pointsPercentageCompleted;
    private double percentageBudgetSpent;
    private double cpi;
    private double spi;
    private int daysPassed;
    private int totalDays;
    private Date currentDate;
    private int remainingSprints;
    
    public ProjectEntity() {
        budget = BigDecimal.ZERO;
        currentSpent = BigDecimal.ZERO;
        pointsCompleted = 0;
        totalPoints = 0;
        pointsPercentageCompleted = 0;
        percentageBudgetSpent = 0;
        cpi=0;
        spi=0;
        daysPassed = 0;
        totalDays = 0;
        currentDate = new Date();
        remainingSprints = 0;
    }
    
    @OneToMany(mappedBy = "projectEntity")
    private List<TaskEntity> taskEntitys;
    
    @ManyToOne
    private UserEntity userEntity;

    public double getTotalPoints() {
        return totalPoints;
    }

    public int getTotalDays() {
        return totalDays;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public void setTotalDays(int totalDays) {
        this.totalDays = totalDays;
    }

    public int getRemainingSprints() {
        return remainingSprints;
    }

    public void setRemainingSprints(int remainingSprints) {
        this.remainingSprints = remainingSprints;
    }

    public double getCpi() {
        return cpi;
    }

    public void setCpi(double cpi) {
        this.cpi = cpi;
    }

    public double getSpi() {
        return spi;
    }

    public int getDaysPassed() {
        return daysPassed;
    }

    public void setDaysPassed(int daysPassed) {
        this.daysPassed = daysPassed;
    }

    public void setSpi(double spi) {
        this.spi = spi;
    }

    public double getPercentageBudgetSpent() {
        return percentageBudgetSpent;
    }

    public void setPercentageBudgetSpent(double percentageBudgetSpent) {
        this.percentageBudgetSpent = percentageBudgetSpent;
    }

    public double getPointsPercentageCompleted() {
        return pointsPercentageCompleted;
    }

    public void setPointsPercentageCompleted(double pointsPercentageCompleted) {
        this.pointsPercentageCompleted = pointsPercentageCompleted;
    }

    public void setTotalPoints(double totalPoints) {
        this.totalPoints = totalPoints;
    }

    public double getPointsCompleted() {
        return pointsCompleted;
    }

    public void setPointsCompleted(double pointsCompleted) {
        this.pointsCompleted = pointsCompleted;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<TaskEntity> getTaskEntitys() {
        return taskEntitys;
    }

    public void setTaskEntitys(List<TaskEntity> taskEntitys) {
        this.taskEntitys = taskEntitys;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public BigDecimal getCurrentSpent() {
        return currentSpent;
    }

    public void setCurrentSpent(BigDecimal currentSpent) {
        this.currentSpent = currentSpent;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectId != null ? projectId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the projectId fields are not set
        if (!(object instanceof ProjectEntity)) {
            return false;
        }
        ProjectEntity other = (ProjectEntity) object;
        if ((this.projectId == null && other.projectId != null) || (this.projectId != null && !this.projectId.equals(other.projectId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProjectEntity[ id=" + projectId + " ]";
    }
    
}
