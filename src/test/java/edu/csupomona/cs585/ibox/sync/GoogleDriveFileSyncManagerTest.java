
package edu.csupomona.cs585.ibox.sync;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import java.io.File;
import org.junit.*;
import org.mockito.Mockito;
import com.google.api.client.http.AbstractInputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.FileList;
import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import com.google.api.services.drive.Drive.Files.Insert;
import com.google.api.services.drive.Drive.Files.Update;



public class GoogleDriveFileSyncManagerTest {

	Drive serviceMockDrive;
	GoogleDriveFileSyncManager driveSyncMngr;
	File file;
	Files files;
	FileList filelist;
	ArrayList<File> arrayList;
	File localFile;
	List list;
	com.google.api.services.drive.model.File body;
	java.util.List<com.google.api.services.drive.model.File> arraylist=new ArrayList<>();


	@Before
	public void initialize ()
	{
		serviceMockDrive=mock(Drive.class);
		driveSyncMngr=new GoogleDriveFileSyncManager(serviceMockDrive);
		file=new File("E/Users/rashaalghofaili/Desktop/test.txt");
		localFile=new File("/Users/rashaalghofaili/Desktop/test.txt");
	}

	@Test
	public void testAddFile()throws IOException
	{

		body =new com.google.api.services.drive.model.File();
		body.setTitle(file.getName());
		body.setId("test");
		Files files=mock(Files.class);
		Insert insert=mock(Insert.class);
		when(serviceMockDrive.files()).thenReturn(files);
		when(files.insert(any(com.google.api.services.drive.model.File.class),any(AbstractInputStreamContent.class))).thenReturn(insert);			
		//when(insert.execute()).thenReturn(body);
		driveSyncMngr.addFile(file);
		verify(insert).execute();
		assertEquals("test", body.getId());



	}

	@Test(expected=IOException.class)
	public void testAddFileIOException() throws IOException{

		body=new com.google.api.services.drive.model.File();
		body.setTitle(file.getName());
		body.setId("test");
		Files files=mock(Files.class);
		Insert insert=mock(Insert.class);
		when(serviceMockDrive.files()).thenReturn(files);
		when(files.insert(any(com.google.api.services.drive.model.File.class),any(AbstractInputStreamContent.class))).thenReturn(insert);
		when(insert.execute()).thenThrow(new IOException());
		driveSyncMngr.addFile(file);
		assertEquals("test", body.getId());

	}


	@Test
	public void testGetField() throws IOException
	{
	 body=new com.google.api.services.drive.model.File();
		body.setTitle("test.txt");
		body.setId("ID1");
		FileList filelist=new FileList();
		arraylist.add(body);
		filelist.setItems(arraylist);
		Files files= mock(Files.class);
		List list= mock(List.class) ;
		when(serviceMockDrive.files()).thenReturn(files);
		when(files.list()).thenReturn(list);
		when(list.execute()).thenReturn(filelist);
		String result=driveSyncMngr.getFileId("test.txt");
		Assert.assertEquals("ID1", result);


	}


	@Test

	public void testUpdateFile() throws IOException
	{


		body=new com.google.api.services.drive.model.File();
		body.setTitle("test.txt");
		body.setId("ID1");
		filelist=new FileList();
		arraylist.add(body);
		filelist.setItems(arraylist);
		files= mock(Files.class);
		list= mock(List.class) ;
		Update update=mock(Files.Update.class);
		when(serviceMockDrive.files()).thenReturn(files);
		when(files.list()).thenReturn(list);
		when(list.execute()).thenReturn(filelist);
		when(files.update(any(String.class),any(com.google.api.services.drive.model.File.class),any(AbstractInputStreamContent.class))).thenReturn(update);
		when(update.execute()).thenReturn(body);
		driveSyncMngr.updateFile(localFile);
		verify(update).execute();
		assertEquals("ID1", body.getId());

	}


	@Test
	public void testDeleteFile() throws IOException
	{

		body=new com.google.api.services.drive.model.File();
		body.setTitle("test.txt");
		body.setId("ID1");
		String filename = "test.txt";
		FileList filelist=new FileList();
		arraylist.add(body);
		filelist.setItems(arraylist);
		Files files= mock(Files.class);
		List list= mock(List.class) ;
		Files.Delete delete=mock(Files.Delete.class);
		when(localFile.getName()).thenReturn(filename);
		when(serviceMockDrive.files()).thenReturn(files);
		when(files.list()).thenReturn(list);
		when(list.execute()).thenReturn(filelist);
		when(files.delete(Mockito.any(String.class))).thenReturn(delete);
		driveSyncMngr.deleteFile(localFile);
		verify(delete).execute();
	}

}


