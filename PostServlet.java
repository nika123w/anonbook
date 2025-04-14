import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/post")
@MultipartConfig
public class PostServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String content = req.getParameter("content");
        Part photo = req.getPart("photo");

        String photoPath = null;
        if (photo != null && photo.getSize() > 0) {
            String fileName = Paths.get(photo.getSubmittedFileName()).getFileName().toString();
            if (!fileName.endsWith(".jpg")) {
                resp.setStatus(400);
                return;
            }
            File uploads = new File("uploaded");
            uploads.mkdir();
            File file = new File(uploads, fileName);
            try (InputStream in = photo.getInputStream()) {
                Files.copy(in, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            photoPath = "/uploaded/" + fileName;
        }

        DatabaseService.getInstance().addPost(content, photoPath);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Post> posts = DatabaseService.getInstance().getAllPosts();
        resp.setContentType("application/json");
        new ObjectMapper().writeValue(resp.getWriter(), posts);
    }
}
