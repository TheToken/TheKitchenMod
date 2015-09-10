package dk.mrspring.kitchen.api.book;

/**
 * Created on 09-09-2015 for TheKitchenMod.
 */
public interface ICookingBookRegistry
{
    void registerChapterHandler(String id, IChapterHandler handler);

    IChapterHandler getChapterHandler(String id);
}
