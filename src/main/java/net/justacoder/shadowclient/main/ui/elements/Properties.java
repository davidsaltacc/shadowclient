package net.justacoder.shadowclient.main.ui.elements;

public abstract class Properties {

    public enum SizeType {
        FILL_PARENT,
        FIT_CHILDREN,
        FIXED_SIZE
    }

    public static class Size {

        public final SizeType type;
        public final int length;

        public Size(SizeType type, int length) {
            if (type != SizeType.FIXED_SIZE) {
                throw new RuntimeException("Can't create a Size supplying a length but not setting the type to fixed size");
            }
            this.type = type;
            this.length = length;
        }

        public Size(SizeType type) {
            if (type == SizeType.FIXED_SIZE) {
                throw new RuntimeException("Can't create a Size not supplying a length but setting the type to fixed size");
            }
            this.type = type;
            this.length = 0;
        }

    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

    public enum CenterAxes {
        ONLY_X,
        ONLY_Y,
        ONLY_X_SAME_Y, // offset to center only on the x-axis, but offset on the y-axis by an equal amount
        ONLY_Y_SAME_X, // offset to center only on the y-axis, but offset on the x-axis by an equal amount
        BOTH
    }

}
