package com.buynsell.buynsell.repository;

import com.buynsell.buynsell.aop.AopInAction;
import com.buynsell.buynsell.model.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.logging.Logger;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostRepositoryTest {

    private static final Logger LOG = Logger.getLogger(PostRepositoryTest.class.getName());

    @Autowired
    PostRepository postRepository;
    private Item actual;

    @Before
    public void setUp() throws Exception {
        prepareTestData();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getItem() {
        LOG.info("PostRepositoryTest.getItem() Begin");
        Item expected = postRepository.getItem(actual.getId());
        assertEquals("Fetching Item by Id",actual.getId(),expected.getId());
        LOG.info("PostRepositoryTest.getItem() End");
    }

    @Test
    public void markSold() {
        LOG.info("PostRepositoryTest.markSold() Begin");
        boolean expected = postRepository.markSold("jainy",actual.getId());
        assertEquals("Check if it changes the availability status when the post is of original user",true, expected);
        expected = postRepository.markSold("jainyy",actual.getId());
        assertEquals("Check if it changes the availability status when the post is of other user",false, expected);
        LOG.info("PostRepositoryTest.markSold() End");
    }

    @Test
    public void markAvailable() {
        LOG.info("PostRepositoryTest.markAvailable() Begin");
        boolean expected = postRepository.markSold("jainy",actual.getId());
        assertEquals("Check if it changes the availability status when the post is of original user",true, expected);
        expected = postRepository.markSold("jainyy",actual.getId());
        assertEquals("Check if it changes the availability status when the post is of other user",false, expected);
        LOG.info("PostRepositoryTest.markAvailable() End");
    }

    private void prepareTestData() {
        actual = new Item();
        actual.setId(1L);
        actual.setTitle("Want to sell car urgently");
        actual.setDescription("It is in a good condition");
        actual.setAvailable(true);
    }
}