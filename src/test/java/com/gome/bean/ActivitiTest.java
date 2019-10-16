package com.gome.bean;

import static org.junit.Assert.*;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class ActivitiTest {
    
    ProcessEngine pEngine = ProcessEngines.getDefaultProcessEngine();
    TaskService taskService;
    FormService formService;
    HistoryService historyService;
    IdentityService identityService;
    ManagementService managementService;
    RepositoryService repositoryService;
    RuntimeService runtimeService;
    

    @Before
    public void init() {
        taskService = pEngine.getTaskService();
        formService = pEngine.getFormService();
        historyService = pEngine.getHistoryService();
        identityService = pEngine.getIdentityService();
        managementService = pEngine.getManagementService();
        repositoryService = pEngine.getRepositoryService();
        runtimeService = pEngine.getRuntimeService();
    }
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//
//    }

    @Test
    public void test() {
//        fail("Not yet implemented");
        System.out.println("流程引擎："+pEngine);
        System.out.println("其他组件：");
    }
    
    @Test
    public void testProcessDefinetionDeployee() {
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource("process/请求流程.bpmn").deploy();

        System.out.println("部署的流程deploy对象"+deployment.getId());
    }
    
    @Test
    public void testQueryProcessDefinetion() {
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        long count = processDefinitionQuery.count();
        System.out.println("流程数量：" + count);
        List<ProcessDefinition> list = processDefinitionQuery.list();
        for(ProcessDefinition pd : list){
            System.out.println("流程id（流程框架生成的）：" + pd.getId());
            System.out.println("流程name（画流程时的流程名）：" + pd.getName());
            System.out.println("流程的key（画流程时的id）：" + pd.getKey());
            System.out.println("流程的资源信息：" + pd.getResourceName() + "==>"
                    + pd.getDiagramResourceName());
            System.out.println("流程的版本号：" + pd.getVersion());
            System.out.println("=====================");
        }
        list = processDefinitionQuery.processDefinitionKeyLike("qjlcID").list();
        for(ProcessDefinition pd : list){
            System.out.println("流程id（流程框架生成的）：" + pd.getId());
            System.out.println("流程name（画流程时的流程名）：" + pd.getName());
            System.out.println("流程的key（画流程时的id）：" + pd.getKey());
            System.out.println("流程的资源信息：" + pd.getResourceName() + "==>"
                    + pd.getDiagramResourceName());
            System.out.println("流程的版本号：" + pd.getVersion());
            System.out.println("=====================");
        }
        list = processDefinitionQuery.processDefinitionNameLike("请假流程").list();
        for(ProcessDefinition pd : list){
            System.out.println("流程id（流程框架生成的）：" + pd.getId());
            System.out.println("流程name（画流程时的流程名）：" + pd.getName());
            System.out.println("流程的key（画流程时的id）：" + pd.getKey());
            System.out.println("流程的资源信息：" + pd.getResourceName() + "==>"
                    + pd.getDiagramResourceName());
            System.out.println("流程的版本号：" + pd.getVersion());
            System.out.println("=====================");
        }
    }
    
    @Test
    public void test04() {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey("qjlcID")
        .latestVersion().singleResult();
        System.out.println("流程的信息："+processDefinition.getId()+"==>"+processDefinition.getName()
                +"===>"+processDefinition.getKey());
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());
        System.out.println("流程启动成功！"+processInstance.getActivityId());
        System.out.println(""+processInstance.getProcessDefinitionId());
    }
    
    @Test
    public void testTask() {
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("qjlcID").list();
        for(Task task : list){
            System.out.println("当前的任务信息：id:"+task.getId());
            System.out.println("任务分給哪个人（由哪个人来处理）："+task.getAssignee());
            System.out.println("任务名："+task.getName());
            String assignee = "zhangsan";
            System.out.println("任务签收；"+assignee+"签收了："+task.getId()+"");
            taskService.claim(task.getId(), "zhangsan");
            System.out.println("===================");
        }
        System.out.println("张三所要执行的任务...");
        List<Task> list2 = taskService.createTaskQuery().taskAssignee("zhangsan").list();
        for (Task task : list2) {
            System.out.println("当前的任务信息：id:"+task.getId());
            System.out.println("任务分給哪个人（由哪个人来处理）："+task.getAssignee());
            System.out.println("任务名："+task.getName());
        }
    }
    
    @Test
    public void testTaskComplete(){
        long count1 = taskService.createTaskQuery().taskAssignee("zhangsan").count();
        System.out.println("任务数量："+count1);
        System.out.println("=====================================");
        List<Task> list2 = taskService.createTaskQuery().taskAssignee("zhangsan").list();
        for (Task task : list2) {
          //完成任务；
            System.out.println("当前的任务信息：id:"+task.getId());
            System.out.println("任务名："+task.getName());
            System.out.println("张三：完成任务"+task.getId());
            taskService.complete(task.getId());
        }
        System.out.println("=====================================");
        long count = taskService.createTaskQuery().taskAssignee("zhangsan").count();
        System.out.println("任务数量："+count);
    }
    
    @Test
    public void testProceTask(){
        List<Task> list = taskService.createTaskQuery().processDefinitionKey("qjlcID").list();
        for (Task task : list) {
            System.out.println("当前的任务信息：id:"+task.getId());
            System.out.println("任务名："+task.getName());
            taskService.complete(task.getId());
        }
        System.out.println("==================");
        
        List<Task> list2 = taskService.createTaskQuery().processDefinitionKey("qjlcID").list();
        for (Task task : list2) {
            System.out.println("当前的任务信息：id:"+task.getId());
            System.out.println("任务名："+task.getName());
            taskService.complete(task.getId());
        }
        
        System.out.println("==================");
        List<Task> list3 = taskService.createTaskQuery().processDefinitionKey("qjlcID").list();
        for (Task task : list3) {
            System.out.println("当前的任务信息：id:"+task.getId());
            System.out.println("任务名："+task.getName());
            //dsadsadsadsadsa
            
            taskService.complete(task.getId());
        }
        
    }

}
