package firsProject.booksProject.QueryRewriter;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.QueryRewriter;

public class MyQueryRewriter implements QueryRewriter {
    @Override
    public String rewrite(String query, Sort sort) {
        return query.replaceAll("original_user_alias", "rewritten_user_alias");
    }
}