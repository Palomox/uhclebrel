package ga.palomox.plugins.uhclebrel.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import main.UHCLebrel;
import skinsrestorer.bukkit.SkinsRestorer;
import util.UHCPlayer;

public class AsyncMethodsTest {
	private ServerMock server;
	private UHCLebrel uhcp;
	PlayerMock p;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		server = MockBukkit.mock();
		uhcp = MockBukkit.load(UHCLebrel.class);
		p  = server.addPlayer();
		MockBukkit.load(SkinsRestorer.class);
	}

	@After
	public void tearDown() throws Exception {
		MockBukkit.unmock();
	}

	@Test
	public void testGetChannelByName() {
		fail("not implemented");
	}

	@Test
	public void testRemoveUHCPlayer() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetUHCPlayerByName() {
		UHCPlayer mp = uhcp.getUHCPlayerByName(p.getName());
		assertTrue(mp.getPlayer().getName().equals(p.getName()));
	}

}
