package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

//    private UserInfo userInfo;

    private Item expectedItem;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        prepareTestData();
    }

    @Test
    public void getItem() {
        Mockito.when(postRepository.getItem(expectedItem.getId()).get()).thenReturn(expectedItem);
        Optional<PostDTO> postDTO = postService.getItem(expectedItem.getId());
        assertEquals("Check if expected item is equal to actual item", expectedItem.getId(), postDTO.get().getId());
    }

    @Test
    public void markSold() {

    }

    @Test
    public void markAvailable() {
    }

    public void prepareTestData(){
        expectedItem = new Item();
        expectedItem.setId(1l);
        expectedItem.setTitle("Car for sale urgently");
        expectedItem.setDescription("This is a very good car with all the conditions");
        expectedItem.setCategory("Vehicles");
        expectedItem.setPrice(25000.00);
        expectedItem.setAvailable(true);
        expectedItem.setCreatedAt(Instant.now());
        expectedItem.setUser(new User("Yash Jain", "jainy", "jainy@arezzosky.com", "shvshgojhsdgjbl", true, null));
        expectedItem.setImages(new HashSet<Image>());
    }
}