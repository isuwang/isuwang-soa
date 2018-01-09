package com.isuwang.soa.monitor.api;

      import com.isuwang.dapeng.core.*;
      import com.isuwang.org.apache.thrift.*;
      import java.util.concurrent.CompletableFuture;
      import java.util.concurrent.Future;
      import java.util.ServiceLoader;
      import com.isuwang.soa.monitor.api.MonitorServiceCodec.*;
      import com.isuwang.soa.monitor.api.service.MonitorServiceAsync;

      /**
       * Autogenerated by Dapeng-Code-Generator (1.2.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated

      **/
      public class MonitorServiceAsyncClient implements MonitorServiceAsync{
      private final String serviceName;
      private final String version;

      private SoaConnectionPool pool;

      public MonitorServiceAsyncClient() {
        this.serviceName = "com.isuwang.soa.monitor.api.service.MonitorService";
        this.version = "1.0.0";

        ServiceLoader<SoaConnectionPoolFactory> factories = ServiceLoader.load(SoaConnectionPoolFactory.class);
        for (SoaConnectionPoolFactory factory: factories) {
          this.pool = factory.getPool();
          break;
        }
        this.pool.registerClientInfo(serviceName,version);
      }

      
          
            /**
            * 

 上送QPS信息

            **/
            
              public CompletableFuture<Void> uploadQPSStat(java.util.List<com.isuwang.soa.monitor.api.domain.QPSStat> qpsStats, long timeout) throws SoaException{

              String methodName = "uploadQPSStat";
              uploadQPSStat_args uploadQPSStat_args = new uploadQPSStat_args();
              uploadQPSStat_args.setQpsStats(qpsStats);
                

              CompletableFuture<uploadQPSStat_result> response = (CompletableFuture<uploadQPSStat_result>) pool.sendAsync(serviceName,version,"uploadQPSStat",uploadQPSStat_args, new UploadQPSStat_argsSerializer(), new UploadQPSStat_resultSerializer(),timeout);

              
                  return response.thenApply((uploadQPSStat_result result )->  null);
                
            }
            
          

        
          
            /**
            * 

 上送平台处理数据

            **/
            
              public CompletableFuture<Void> uploadPlatformProcessData(java.util.List<com.isuwang.soa.monitor.api.domain.PlatformProcessData> platformProcessDatas, long timeout) throws SoaException{

              String methodName = "uploadPlatformProcessData";
              uploadPlatformProcessData_args uploadPlatformProcessData_args = new uploadPlatformProcessData_args();
              uploadPlatformProcessData_args.setPlatformProcessDatas(platformProcessDatas);
                

              CompletableFuture<uploadPlatformProcessData_result> response = (CompletableFuture<uploadPlatformProcessData_result>) pool.sendAsync(serviceName,version,"uploadPlatformProcessData",uploadPlatformProcessData_args, new UploadPlatformProcessData_argsSerializer(), new UploadPlatformProcessData_resultSerializer(),timeout);

              
                  return response.thenApply((uploadPlatformProcessData_result result )->  null);
                
            }
            
          

        
          
            /**
            * 

 上送DataSource信息

            **/
            
              public CompletableFuture<Void> uploadDataSourceStat(java.util.List<com.isuwang.soa.monitor.api.domain.DataSourceStat> dataSourceStat, long timeout) throws SoaException{

              String methodName = "uploadDataSourceStat";
              uploadDataSourceStat_args uploadDataSourceStat_args = new uploadDataSourceStat_args();
              uploadDataSourceStat_args.setDataSourceStat(dataSourceStat);
                

              CompletableFuture<uploadDataSourceStat_result> response = (CompletableFuture<uploadDataSourceStat_result>) pool.sendAsync(serviceName,version,"uploadDataSourceStat",uploadDataSourceStat_args, new UploadDataSourceStat_argsSerializer(), new UploadDataSourceStat_resultSerializer(),timeout);

              
                  return response.thenApply((uploadDataSourceStat_result result )->  null);
                
            }
            
          

        

      /**
      * getServiceMetadata
      **/
      public String getServiceMetadata() throws SoaException {
        String methodName = "getServiceMetadata";
        getServiceMetadata_args getServiceMetadata_args = new getServiceMetadata_args();
        getServiceMetadata_result response = pool.send(serviceName,version,methodName,getServiceMetadata_args, new GetServiceMetadata_argsSerializer(), new GetServiceMetadata_resultSerializer());
        return response.getSuccess();
      }

    }
    