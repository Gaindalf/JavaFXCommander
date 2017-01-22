package sample.intarface;

import sample.objects.Columns;

public interface TableBook {
    void add(Columns columns);
    void update(Columns columns);
    void delete(Columns columns);
}
