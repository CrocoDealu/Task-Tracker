package taskTracker.utils;

import taskTracker.enums.CommandStrategy;

public final class ComandParser {
    private static ComandParser comandParser;

    private ComandParser() {}

    public static ComandParser getInstance() {
        if (comandParser == null)
            comandParser = new ComandParser();
        return comandParser;
    }

    public static String[] parse(String args, CommandStrategy commandStrategy) {
        switch (commandStrategy) {
            case ADD -> {
                return parseAdd(args);
            }
            case UPDATE -> {
                return parseUpdate(args);
            }
            case DELETE -> {
                return parseDelete(args);
            }
            case MARK_DONE -> {
                return parseMarkDone(args);
            }
            case MARK_IN_PROGRESS -> {
                return parseMarkInProgress(args);
            }
            default -> {
                System.out.println("Command unknoun\n");
                return null;
            }
        }
    }

    private static String[] parseMarkDone(String args) {
        String[] cut = args.split(" ");

        if (cut.length == 2) {
            String tid = cut[1];

            try {
                Integer.parseInt(tid);
            } catch (NumberFormatException e) {
                throw new RuntimeException("The ID is not a valid integer.");
            }
            return new String[]{tid};
        }
        throw new RuntimeException("use case: mark-done 1(tid)");
    }

    private static String[] parseMarkInProgress(String args) {
        String[] cut = args.split(" ");

        if (cut.length == 2) {
            String tid = cut[1];

            try {
                Integer.parseInt(tid);
            } catch (NumberFormatException e) {
                throw new RuntimeException("The ID is not a valid integer.");
            }
            return new String[]{tid};
        }
        throw new RuntimeException("use case: mark-in-progress 1(tid)");
    }

    private static String[] parseUpdate(String args) {
        String[] cut = args.split(" ");
        int firstIndex = args.indexOf('"');
        int secondIndex = args.indexOf('"', firstIndex + 1);
        int thirdIndex = args.indexOf('"', secondIndex + 1);
        int lastIndex = args.lastIndexOf('"');
        if (cut.length >= 3 || lastIndex == -1 || firstIndex == lastIndex || secondIndex == lastIndex || thirdIndex == lastIndex) {
            String[] result = new String[3];
            String tid = cut[1];

            try {
                Integer.parseInt(tid);
            } catch (NumberFormatException e) {
                throw new RuntimeException("The ID is not a valid integer.");
            }
            result[0] = tid;
            result[1] = cut[2];
            result[2] = cut[3];
            return result;
        }
        throw new RuntimeException("use case: update 1(tid) \"Title\" \"new description\"");
    }

    private static String[] parseAdd(String args)  {
        int firstIndex = args.indexOf('"');
        int secondIndex = args.indexOf('"', firstIndex + 1);
        int thirdIndex = args.indexOf('"', secondIndex + 1);
        int lastIndex = args.lastIndexOf('"');
        if (firstIndex == -1
                || lastIndex == -1
                || firstIndex == lastIndex
                || (lastIndex + 1 != args.length())
                || firstIndex == secondIndex
                || secondIndex == thirdIndex
                || thirdIndex == lastIndex
                || secondIndex == lastIndex)
            throw new RuntimeException("use case: add \"task description\"\n");

        return new String[]{args.substring(firstIndex + 1, secondIndex), args.substring(thirdIndex + 1, lastIndex)};
    }

    private static String[] parseDelete(String args) {
        String[] cut = args.split(" ");
        if (cut.length == 2) {
            String s = cut[1];
            try {
                int id = Integer.parseInt(s);
            } catch (Exception e) {
                throw new RuntimeException("use case: delete id \n");
            }
            return new String[]{s};
        }
        throw new RuntimeException("use case: delete id \n");
    }
}