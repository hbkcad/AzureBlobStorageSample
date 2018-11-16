package azure.storage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.BlobRequestOptions;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import com.microsoft.azure.storage.blob.ListBlobItem;

public class StorageAccountDemo {

	public static void main(String[] args) {

	
			  final String storageConnectionString = "*******************";


				File sourceFile = null, downloadedFile = null;
				System.out.println("Azure Blob storage quick start sample");

				CloudStorageAccount storageAccount;
				CloudBlobClient blobClient = null;
				CloudBlobContainer container=null;

				try {    
					// Parse the connection string and create a blob client to interact with Blob storage
					storageAccount = CloudStorageAccount.parse(storageConnectionString);
					blobClient = storageAccount.createCloudBlobClient();
					container = blobClient.getContainerReference("quickstartcontainer");

					// Create the container if it does not exist with public access.
					System.out.println("Creating container: " + container.getName());
					container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());		    

					//Creating a sample file
					sourceFile = new File("sample.txt");
					System.out.println("Creating a sample file at: " + sourceFile.toString());
					Writer output = new BufferedWriter(new FileWriter(sourceFile));
					output.write("Hello Azure!");
					output.close();

					//Getting a blob reference
					CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());

					//Creating blob and uploading file to it
					System.out.println("Uploading the sample file ");
					blob.uploadFromFile(sourceFile.getAbsolutePath());
					

					//Listing contents of container
					for (ListBlobItem blobItem : container.listBlobs()) {
					System.out.println("URI of blob is: " + blobItem.getUri());
				}

				
				downloadedFile = new File(sourceFile.getParentFile(), "downloadedFile.txt");
				blob.downloadToFile(downloadedFile.getAbsolutePath());
				} 
				catch (StorageException ex)
				{
					System.out.println(String.format("Error. Http code: %d and error code: %s", ex.getHttpStatusCode(), ex.getErrorCode()));
				}
				catch (Exception ex) 
				{
					System.out.println(ex.getMessage());
				}
				finally 
				{
					System.out.println("The program has completed successfully.");

					
				}
			}
		
		
		
	}


