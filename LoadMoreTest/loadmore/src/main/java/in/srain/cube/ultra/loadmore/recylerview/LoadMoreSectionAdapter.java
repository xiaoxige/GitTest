package in.srain.cube.ultra.loadmore.recylerview;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

/**
 * Created by dee on 15/12/3.
 */
public abstract class LoadMoreSectionAdapter<F extends RecyclerView.ViewHolder,H extends RecyclerView.ViewHolder,VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter{

    protected static final int TYPE_SECTION_HEADER = -1;
    protected static final int TYPE_ITEM = -2;
    protected static final int TYPE_HEADER = -3;

    private int[] sectionForPosition = null;
    private int[] positionWithinSection = null;
    private boolean[] isHeader = null;
    private int count = 0;

    private boolean showHeader = false;

    public LoadMoreSectionAdapter() {
        super();
        registerAdapterDataObserver(new SectionDataObserver());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        setupIndices();
    }

    /**
     * Returns the sum of number of items for each section plus headers and footers if they
     * are provided.
     */

    @Override
    public int getItemCount() {
        return count;
    }

    private void setupIndices(){
        count = countItems() + (isShowHeader() ? 1 : 0);
        allocateAuxiliaryArrays(count);
        precomputeIndices();
    }

    private int countItems() {
        int count = 0;
        int sections = getSectionCount();

        for(int i = 0; i < sections; i++){
            count += 1 + getItemCountForSection(i);
        }
        return count;
    }

    private void precomputeIndices(){
        int sections = getSectionCount();
        int index = (isShowHeader()?1:0);

        for(int i = isShowHeader()?1:0; i < (isShowHeader()?sections+1:sections); i++){
            setPrecomputedItem(index, true, i, 0);
            index++;

            for(int j = 0; j < getItemCountForSection(i - (isShowHeader()?1:0)); j++){
                setPrecomputedItem(index, false, i, j);
                index++;
            }
        }
    }

    private void allocateAuxiliaryArrays(int count) {
        sectionForPosition = new int[count];
        positionWithinSection = new int[count];
        isHeader = new boolean[count];
    }

    private void setPrecomputedItem(int index, boolean isHeader, int section, int position) {
        this.isHeader[index] = isHeader;
        sectionForPosition[index] = section;
        positionWithinSection[index] = position;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        if(isHeaderViewType(viewType)){
            viewHolder = onCreateHeaderViewHolder(parent,viewType);
        }else if(isSectionHeaderViewType(viewType)) {
            viewHolder = onCreateSectionHeaderViewHolder(parent, viewType);
        }else{
            viewHolder = onCreateItemViewHolder(parent, viewType);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isShowHeader() && position == 0){
            onBindHeaderViewHolder((F)holder,position);
            return;
        }
        int section = sectionForPosition[position];
        int index = positionWithinSection[position];

        if(isSectionHeaderPosition(position)){
            onBindSectionHeaderViewHolder((H) holder, isShowHeader()?section - 1:section);
        }else{
            onBindItemViewHolder((VH) holder, isShowHeader()?section - 1:section, index);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isShowHeader() && position == 0){
            return getHeaderViewType(position);
        }
        if(sectionForPosition == null){
            setupIndices();
        }

        int section = sectionForPosition[position];
        int index = positionWithinSection[position];

        if(isSectionHeaderPosition(position)){
            return getSectionHeaderViewType(section);
        }else{
            return getSectionItemViewType(section, index);
        }
    }
    protected int getHeaderViewType(int section){
        return TYPE_HEADER;
    }
    protected int getSectionHeaderViewType(int section){
        return TYPE_SECTION_HEADER;
    }

    protected int getSectionItemViewType(int section, int position){
        return TYPE_ITEM;
    }

    protected int getPositionForSection(int section){
        int index = 0;
        for (int i = 0; i < section; i++) {
            index += getItemCountForSection(i) + 1;
        }
        return index + (isShowHeader()?1:0);
    }
    /**
     * Returns true if the argument position corresponds to a header
     */
    public boolean isSectionHeaderPosition(int position){
        if(isHeader == null){
            setupIndices();
        }
        return isHeader[position];
    }

    protected boolean isSectionHeaderViewType(int viewType){
        return viewType == TYPE_SECTION_HEADER;
    }
    protected boolean isHeaderViewType(int viewType){
        return viewType == TYPE_HEADER;
    }
    protected abstract boolean isShowHeader();
    /**
     * Returns the number of sections in the RecyclerView
     */
    protected abstract int getSectionCount();

    /**
     * Returns the number of items for a given section
     */
    protected abstract int getItemCountForSection(int section);
    /**
     * Creates a ViewHolder of class H for a Header
     */
    protected abstract F  onCreateHeaderViewHolder(ViewGroup parent, int viewType);
    /**
     * Creates a ViewHolder of class H for a Header
     */
    protected abstract H  onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType);

    /**
     * Creates a ViewHolder of class VH for an Item
     */
    protected abstract VH onCreateItemViewHolder(ViewGroup parent, int viewType);
    /**
     * Binds data to the header view of a given section
     */
    protected abstract void onBindHeaderViewHolder(F holder, int section);
    /**
     * Binds data to the header view of a given section
     */
    protected abstract void onBindSectionHeaderViewHolder(H holder, int section);

    /**
     * Binds data to the item view for a given position within a section
     */
    protected abstract void onBindItemViewHolder(VH holder, int section, int position);

    class SectionDataObserver extends RecyclerView.AdapterDataObserver{
        @Override
        public void onChanged() {
            setupIndices();
        }
    }


}
