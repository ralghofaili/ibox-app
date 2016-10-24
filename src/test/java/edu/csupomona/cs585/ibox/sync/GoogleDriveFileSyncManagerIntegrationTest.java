package edu.csupomona.cs585.ibox.sync;
import static org.junit.Assert.*;

import java.io.File;
import org.junit.*;
import edu.csupomona.cs585.ibox.sync.GoogleDriveFileSyncManager;
import edu.csupomona.cs585.ibox.sync.GoogleDriveServiceProvider;
import java.io.IOException;

public class GoogleDriveFileSyncManagerIntegrationTest {

	GoogleDriveFileSyncManager driveMng = new GoogleDriveFileSyncManager(

			GoogleDriveServiceProvider.get().getGoogleDriveClient());
	File file = new File("/Users/rashaalghofaili/Desktop/test.txt");

	@Test
	public void testAddFile() throws IOException {
		driveMng.addFile(file);
		assertNotNull(driveMng.getFileId(file.getName()));

	}

	@Test
	public void testUpdateFile() throws IOException {
		driveMng.updateFile(file);
		assertNotNull(driveMng.getFileId(file.getName()));

	}

	@Test
	public void testDeleteFile()throws IOException {
		driveMng.deleteFile(file);
	}
}
