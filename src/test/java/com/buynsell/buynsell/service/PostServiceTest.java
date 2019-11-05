package com.buynsell.buynsell.service;

import com.buynsell.buynsell.model.Image;
import com.buynsell.buynsell.model.Item;
import com.buynsell.buynsell.model.User;
import com.buynsell.buynsell.payload.CreatePostDTO;
import com.buynsell.buynsell.payload.PostDTO;
import com.buynsell.buynsell.model.UserInfo;
import com.buynsell.buynsell.repository.PostRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserService userService;

    @Mock
    private UserInfo userInfo;

    private UserInfo expectedUserInfo;

    private Item expectedItem;

    private CreatePostDTO createPostDTO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        prepareTestData();
    }

    @Test
    public void createPost() throws Exception {
        Item item = Mockito.any();
        Optional<User> user = Optional.of(new User());
        Mockito.when(postRepository.createPost(item)).thenReturn(item);
        Mockito.when(userService.findByUsernameOrEmail(expectedUserInfo.getEmail())).thenReturn(user);
        Mockito.when(userInfo.getEmail()).thenReturn(expectedUserInfo.getEmail());
        Item actualItem = postService.createPost(createPostDTO);

        verify(userInfo, times(1)).getEmail();

    }

    @Test
    public void getItem() throws Exception {
        Mockito.when(postRepository.getItem(expectedItem.getId())).thenReturn(Optional.ofNullable(expectedItem));
        Optional<PostDTO> postDTO = postService.getItem(expectedItem.getId());
        assertEquals("Check if expected item is equal to actual item", expectedItem.getId(), postDTO.get().getId());
    }

    @Test
    public void markSold() {

    }

    @Test
    public void markAvailable() {
    }

    public void prepareTestData() {
        expectedUserInfo = new UserInfo("jainy@arezzosky.com", "jainy");

        expectedItem = new Item();
        expectedItem.setId(1l);
        expectedItem.setTitle("Car for sale Test");
        expectedItem.setDescription("Test Description,Test Description,Test Description,Test Description");
        expectedItem.setCategory("Test");
        expectedItem.setPrice(2000.00);
        expectedItem.setAvailable(true);
        expectedItem.setCreatedAt(Instant.now());
        expectedItem.setUser(new User("Yash Jain", "jainy", "jainy@arezzosky.com", "shvshgojhsdgjbl", true, null));
        expectedItem.setImages(new HashSet<Image>());

        createPostDTO = new CreatePostDTO();
        createPostDTO.setTitle("Car for sale Test");
        createPostDTO.setDescription("Test Description,Test Description,Test Description,Test Description");
        createPostDTO.setCategory("Test");
        createPostDTO.setPrice("2000");
        MultipartFile images[] = new MultipartFile[0];
        createPostDTO.setImages(images);
    }
}