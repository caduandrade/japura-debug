package org.japura.debug.tasks.executions;

import java.util.Collection;

import org.japura.task.Task;
import org.japura.task.TaskStatus;
import org.japura.task.messages.notify.TaskExecutionMessage;
import org.japura.util.info.InfoNode;

/**
 * <P>
 * Copyright (C) 2013 Carlos Eduardo Leite de Andrade
 * <P>
 * This library is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * <P>
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <P>
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <A
 * HREF="www.gnu.org/licenses/">www.gnu.org/licenses/</A>
 * <P>
 * For more information, contact: <A HREF="www.japura.org">www.japura.org</A>
 * <P>
 * 
 * @author Carlos Eduardo Leite de Andrade
 */
public class Execution{

  private int level;
  private String sessionId;
  private boolean parallel;
  private String taskId;
  private String taskClass;
  private String taskName;
  private Collection<InfoNode> taskInfoNodes;
  private Collection<InfoNode> sessionInfoNodes;
  private Collection<InfoNode> executorInfoNodes;
  private Collection<InfoNode> contextInfoNodes;
  private TaskStatus status;

  public Execution(TaskExecutionMessage msg) {
	this.parallel = msg.isParallel();

	Task task = msg.getTask();
	this.sessionId = msg.getSessionId();

	String packageName = task.getClass().getPackage().getName();
	if (packageName.length() > 0) {
	  packageName += ".";
	}
	String className = task.getClass().getName();
	this.taskClass =
		className.substring(packageName.length(), className.length());
	this.taskId = task.getId();
	this.level = task.getNestedLevel();
	this.taskName = task.getName();
	this.status = task.getStatus();

	this.taskInfoNodes = task.getInfoNodes();
	this.executorInfoNodes = task.getTaskExecutor().getInfoNodes();
	this.sessionInfoNodes = msg.getSessionInfoNodes();
	this.contextInfoNodes = msg.getContextInfoNodes();
  }

  public Collection<InfoNode> getContextInfoNodes() {
	return contextInfoNodes;
  }

  public Collection<InfoNode> getTaskInfoNodes() {
	return taskInfoNodes;
  }

  public Collection<InfoNode> getExecutorInfoNodes() {
	return executorInfoNodes;
  }

  public Collection<InfoNode> getSessionInfoNodes() {
	return sessionInfoNodes;
  }

  public boolean isParallel() {
	return parallel;
  }

  public String getTaskId() {
	return taskId;
  }

  public String getTaskClass() {
	return taskClass;
  }

  public String getTaskName() {
	return taskName;
  }

  public TaskStatus getStatus() {
	return status;
  }

  public String getSessionId() {
	return sessionId;
  }

  public int getLevel() {
	return level;
  }

}
