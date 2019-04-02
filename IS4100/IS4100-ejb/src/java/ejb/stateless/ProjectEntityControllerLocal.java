/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.ProjectEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alastair
 */
@Local
public interface ProjectEntityControllerLocal {

    public List<ProjectEntity> retrieveProjectByUserId(Long userId);

    public ProjectEntity createNewProject(ProjectEntity projectEntity);

    public ProjectEntity retrieveProjectByProjectId(Long id);

    public void updateProject(ProjectEntity projectEntity);

    
}
