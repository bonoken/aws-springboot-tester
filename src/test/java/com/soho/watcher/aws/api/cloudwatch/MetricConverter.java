package com.soho.watcher.aws.api.cloudwatch;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.soho.comm.string.CaseConverter;
import com.soho.watcher.aws.api.AwsConfiguration;



@RunWith(SpringRunner.class)
@SpringBootTest
public class MetricConverter {
	
	/** log */
	private static final Logger logger = LogManager.getLogger(MetricConverter.class);
	
	@Test
	public void contextLoads()  {
		
		//printElastiCache();
		//printElasticsearch();
		//printRedshift();
		//printECS();
		//printCloudFront();
		//printApiGateway();
		//printS3();
		//printEC2();
		printAS();
	}
	
	public List<String> getMetricList(String[] list) {
		List<String> metricList = new ArrayList<String>();
		
		for(String metricName : list) {
			if(!metricList.contains(metricName)) {
				metricList.add(metricName);
			} else {
				System.out.println("중복 : " + metricName);
			}
		}
		return metricList;
	}
	
	
	public void printMetricList(List<String> metricList, String ci_type) {
		
		int indexStart = 1; 
		for(String metricName : metricList) {
			String indexStr = "";
			if(indexStart < 10) {
				indexStr = "0"+ String.valueOf(indexStart);
			} else {
				indexStr = String.valueOf(indexStart);
			}
			String metricId = ci_type+":"+indexStr;
			indexStart ++;
			System.out.println( CaseConverter.camelToUnderScore(metricName) + "(\""+metricName+"\",\""+ metricId +"\"),");
		}
	}
	
	public void printSimpleMetricList(List<String> metricList) {
		
		for(String metricName : metricList) {
			
			System.out.println(metricName);
		}
	}
	
	
	public void printRDS() {
		String[] list = {
				"BinLogDiskUsage"
				,"CPUUtilization"
				,"CPUCreditUsage"
				,"CPUCreditBalance"
				,"DatabaseConnections"
				,"DiskQueueDepth"
				,"FreeableMemory"
				,"FreeStorageSpace"
				,"ReplicaLag"
				,"SwapUsage"
				,"ReadIOPS"
				,"WriteIOPS"
				,"ReadLatency"
				,"WriteLatency"
				,"ReadThroughput"
				,"WriteThroughput"
				,"NetworkReceiveThroughput"
				,"NetworkTransmitThroughput"
				,"BurstBalance"
				,"BufferCacheHitRatio"
				,"CPUSurplusCreditBalance"
				,"CPUSurplusCreditsCharged"
				,"MaximumUsedTransactionIDs"
				,"OldestReplicationSlotLag"
				,"ReplicationSlotDiskUsage"
				,"TransactionLogsDiskUsage"
				,"TransactionLogsGeneration"
				// Aurora
				,"ActiveTransactions"
				,"AuroraBinlogReplicaLag"
				,"AuroraReplicaLag"
				,"AuroraReplicaLagMaximum"
				,"AuroraReplicaLagMinimum"
				,"BinLogDiskUsage"
				,"BlockedTransactions"
				//,"BufferCacheHitRatio"
				,"CommitLatency"
				,"CommitThroughput"
				//,"CPUCreditBalance"
				//,"CPUCreditUsage"
				//,"CPUUtilization"
				//,"DatabaseConnections"
				,"DDLLatency"
				,"DDLThroughput"
				,"Deadlocks"
				,"DeleteLatency"
				,"DeleteThroughput"
				//,"DiskQueueDepth"
				,"DMLLatency"
				,"DMLThroughput"
				,"EngineUptime"
				//,"FreeableMemory"
				,"FreeLocalStorage"
				,"InsertLatency"
				,"InsertThroughput"
				,"LoginFailures"
				//,"MaximumUsedTransactionIDs"
				//,"NetworkReceiveThroughput"
				,"NetworkThroughput"
				//,"NetworkTransmitThroughput"
				,"Queries"
				//,"ReadIOPS"
				//,"ReadLatency"
				//,"ReadThroughput"
				,"ResultSetCacheHitRatio"
				,"SelectLatency"
				,"SelectThroughput"
				//,"SwapUsage"
				//,"TransactionLogsDiskUsage"
				,"UpdateLatency"
				,"UpdateThroughput"
				,"VolumeBytesUsed"
				,"VolumeReadIOPs"
				,"VolumeWriteIOPs"
				//,"WriteIOPS"
				//,"WriteLatency"
				,"WriteThroughput"
		};
		
		
		List<String> metricList = getMetricList(list);
		//printMetricList(metricList, "3.44.2");
		printSimpleMetricList(metricList);
	}
	
	
	public void printElasticsearch() {
		String[] list = {
				"ClusterStatus.green"
				,"ClusterStatus.green"
				,"ClusterStatus.yellow"
				,"ClusterStatus.red"
				,"Nodes"
				,"SearchableDocuments"
				,"DeletedDocuments"
				,"CPUUtilization"
				,"FreeStorageSpace"
				,"ClusterUsedSpace"
				,"ClusterIndexWritesBlocked"
				,"JVMMemoryPressure"
				,"AutomatedSnapshotFailure"
				,"CPUCreditBalance"
				,"KibanaHealthyNodes"
				,"KMSKeyError"
				,"KMSKeyInaccessible"
				,"MasterCPUUtilization"
				,"MasterFreeStorageSpace"
				,"MasterJVMMemoryPressure"
				,"MasterCPUCreditBalance"
				,"MasterReachableFromNode"
				,"ReadLatency"
				,"WriteLatency"
				,"ReadThroughput"
				,"WriteThroughput"
				,"DiskQueueDepth"
				,"ReadIOPS"
				,"WriteIOPS"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.10");
		//printSimpleMetricList(metricList);
	}
	
	public void printElastiCache() {
		String[] list = {
				"CPUUtilization"
				,"FreeableMemory"
				,"NetworkBytesIn"
				,"NetworkBytesOut"
				,"SwapUsage"
				,"BytesReadIntoMemcached"
				,"BytesUsedForCacheItems"
				,"BytesWrittenOutFromMemcached"
				,"CasBadval"
				,"CasHits"
				,"CasMisses"
				,"CmdFlush"
				,"CmdGet"
				,"CmdSet"
				,"CurrConnections"
				,"CurrItems"
				,"DecrHits"
				,"DecrMisses"
				,"DeleteHits"
				,"DeleteMisses"
				,"Evictions"
				,"GetHits"
				,"GetMisses"
				,"IncrHits"
				,"IncrMisses"
				,"Reclaimed"
				,"BytesUsedForHash"
				,"CmdConfigGet"
				,"CmdConfigSet"
				,"CmdTouch"
				,"CurrConfig"
				,"EvictedUnfetched"
				,"ExpiredUnfetched"
				,"SlabsMoved"
				,"TouchHits"
				,"TouchMisses"
				,"NewConnections"
				,"NewItems"
				,"UnusedMemory"
				,"BytesUsedForCache"
				,"CacheHits"
				,"CacheMisses"
				,"CurrConnections"
				,"EngineCPUUtilization"
				,"Evictions"
				,"HyperLogLogBasedCmds"
				,"NewConnections"
				,"Reclaimed"
				,"ReplicationBytes"
				,"ReplicationLag"
				,"SaveInProgress"
				,"CurrItems"
				,"GetTypeCmds"
				,"HashBasedCmds"
				,"KeyBasedCmds"
				,"ListBasedCmds"
				,"SetBasedCmds"
				,"SetTypeCmds"
				,"SortedSetBasedCmds"
				,"StringBasedCmds"

		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.11");
		//printSimpleMetricList(metricList);
	}
	
	public void printRedshift() {
		String[] list = {
				"CPUUtilization"
				,"DatabaseConnections"
				,"HealthStatus"
				,"MaintenanceMode"
				,"NetworkReceiveThroughput"
				,"NetworkTransmitThroughput"
				,"PercentageDiskSpaceUsed"
				,"ReadIOPS"
				,"ReadLatency"
				,"ReadThroughput"
				,"WriteIOPS"
				,"WriteLatency"
				,"WriteThroughput"

		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.4");
		//printSimpleMetricList(metricList);
	}
	
	
	public void printECS() {
		String[] list = {
				"CPUReservation"
				,"CPUUtilization"
				,"MemoryReservation"
				,"MemoryUtilization"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.20");
		//printSimpleMetricList(metricList);
	}
	
	public void printCloudFront() {
		String[] list = {
				"Requests"
				,"BytesDownloaded"
				,"BytesUploaded"
				,"TotalErrorRate"
				,"4xxErrorRate"
				,"5xxErrorRate"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.5");
		//printSimpleMetricList(metricList);
	}
	
	public void printApiGateway() {
		String[] list = {
				"4XXError"
				,"5XXError"
				,"CacheHitCount"
				,"CacheMissCount"
				,"Count"
				,"IntegrationLatency"
				,"Latency"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.12");
		//printSimpleMetricList(metricList);
	}
	
	public void printS3() {
		String[] list = {
				"BucketSizeBytes"
				,"NumberOfObjects"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.13");
		//printSimpleMetricList(metricList);
	}
	
	public void printEC2() {
		String[] list = {
				// 상태지
				"StatusCheckFailed"
				,"StatusCheckFailed_Instance"
				,"StatusCheckFailed_System"
				//
				,"CPUUtilization"
				,"DiskReadOps"
				,"DiskWriteOps"
				,"DiskReadBytes"
				,"DiskWriteBytes"
				,"NetworkIn"
				,"NetworkOut"
				,"NetworkPacketsIn"
				,"NetworkPacketsOut"
				//T2 인스턴스에 대한 다음 CPU 크레딧 지표
				,"CPUCreditUsage"
				,"CPUCreditBalance"
				,"CPUSurplusCreditBalance"
				,"CPUSurplusCreditsCharged"
				// C5 및 M5 인스턴스를 위해 다음과 같은 Amazon EBS 지표
				,"EBSReadOps"
				,"EBSWriteOps"
				,"EBSReadBytes"
				,"EBSWriteBytes"
				,"EBSIOBalance%"
				,"EBSByteBalance%"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.15");
		//printSimpleMetricList(metricList);
	}
	
	public void printAS() {
		String[] list = {
				// 상태지
				"GroupMinSize"
				,"GroupMaxSize"
				,"GroupDesiredCapacity"
				,"GroupInServiceInstances"
				,"GroupPendingInstances"
				,"GroupStandbyInstances"
				,"GroupTerminatingInstances"
				,"GroupTotalInstances"
		};
		
		
		List<String> metricList = getMetricList(list);
		printMetricList(metricList, "3.44.16");
		//printSimpleMetricList(metricList);
	}
}
