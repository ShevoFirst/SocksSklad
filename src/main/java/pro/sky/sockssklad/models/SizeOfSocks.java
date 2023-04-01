package pro.sky.sockssklad.models;

public enum SizeOfSocks {
    XS(39),
    S(40),
    M(41),
    L(42),
    XL(43);
    final int size;//

    SizeOfSocks(int size) {
        this.size = size;
    }

    public int getIntSizeOfSocks() {
        return size;
    }
}