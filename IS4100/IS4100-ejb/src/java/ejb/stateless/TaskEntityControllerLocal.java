/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.stateless;

import entity.TaskEntity;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alastair
 */
@Local
public interface TaskEntityControllerLocal {

    public TaskEntity createNewTask(TaskEntity taskEntity, Long projectId);

    public List<TaskEntity> retrieveTaskEntitiesByProjectId(Long id);

    public TaskEntity retrieveTaskByTaskId(Long id);

    public void updateTask(TaskEntity taskEntity, Long projectId, boolean create);

    public void deleteTask(Long taskId);
    
}
