package com.gitee.search.models;

import com.gitee.search.core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.document.*;
import org.apache.lucene.facet.FacetField;

/**
 * Source File Object
 * @author Winter Lau<javayou@gmail.com>
 */
public final class SourceFile extends Searchable {

    private String uuid;            //file unique identify
    private Relation repository;    //repository, use this field to delete all files of repository

    private String name;
    private String location;        // Path to file relative to repo location
    private String contents;
    private String hash;            //content sha1 hash
    private String codeOwner;
    private String language;

    private int lines;              // How many lines in the file
    private int codeLines;          // How many lines are code
    private int commentLines;       // How many lines are comments
    private int blankLines;         // How many lines are blank
    private int complexity;         // Complexity calculation taken from scc

    private String revision;        //last commit id

    /**
     * generate lucene document
     *
     * @return
     */
    @Override
    public Document getDocument() {
        Document document = new Document();

        // Uuid is the primary key for documents
        document.add(new StringField(Constants.FIELD_UUID, uuid, Field.Store.YES));

        //文档维度
        if (StringUtils.isNotBlank(language))
            document.add(new FacetField(Constants.FIELD_LANGUAGE,     this.getLanguage()));
            document.add(new FacetField(Constants.FIELD_REPO_NAME,    this.repository.name));
        if (StringUtils.isNotBlank(codeOwner))
            document.add(new FacetField(Constants.FIELD_CODE_OWNER,   this.getCodeOwner()));

        //仓库信息
        document.add(new NumericDocValuesField(Constants.FIELD_REPO_ID, this.repository.id));
        document.add(new StoredField(Constants.FIELD_REPO_ID,           this.repository.id));
        document.add(new StringField(Constants.FIELD_REPO_NAME,         this.repository.name,     Field.Store.YES));
        document.add(new StringField(Constants.FIELD_REPO_URL,          this.repository.url,      Field.Store.YES));

        //文件信息
        document.add(new TextField(Constants.FIELD_FILE_NAME,       this.getName(),         Field.Store.YES));
        document.add(new StringField(Constants.FIELD_FILE_LOCATION, this.getLocation(),     Field.Store.YES));
        document.add(new TextField(Constants.FIELD_SOURCE,          this.getContents(),     Field.Store.YES));

        //文件属性
        document.add(new TextField(Constants.FIELD_CODE_OWNER,      this.getCodeOwner(),    Field.Store.YES));
        document.add(new StringField(Constants.FIELD_LANGUAGE,      this.getLanguage(),     Field.Store.YES));
        document.add(new StoredField(Constants.FIELD_FILE_HASH,     this.getHash()));

        //文件统计信息
        document.add(new StoredField(Constants.FIELD_LINES_TOTAL,   this.getLines()));
        document.add(new StoredField(Constants.FIELD_LINES_CODE,    this.getCodeLines()));
        document.add(new StoredField(Constants.FIELD_LINES_BLANK,   this.getBlankLines()));
        document.add(new StoredField(Constants.FIELD_LINES_COMMENT, this.getCommentLines()));
        document.add(new StoredField(Constants.FIELD_COMPLEXITY,    this.getComplexity()));

        document.add(new StringField(Constants.FIELD_REVISION,      this.getRevision(),     Field.Store.YES));

        // Extra metadata in this case when it was last indexed
        long indexTime = System.currentTimeMillis();
        document.add(new SortedNumericDocValuesField(Constants.FIELD_LAST_INDEX, indexTime));
        document.add(new StoredField(Constants.FIELD_LAST_INDEX, indexTime));

        return document;
    }

    /**
     * Read fields from document
     *
     * @param doc
     */
    @Override
    public Searchable setDocument(Document doc) {
        return null;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Relation getRepository() {
        return repository;
    }

    public void setRepository(Relation repository) {
        this.repository = repository;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCodeOwner() {
        return codeOwner;
    }

    public void setCodeOwner(String codeOwner) {
        this.codeOwner = codeOwner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }

    public int getCodeLines() {
        return codeLines;
    }

    public void setCodeLines(int codeLines) {
        this.codeLines = codeLines;
    }

    public int getCommentLines() {
        return commentLines;
    }

    public void setCommentLines(int commentLines) {
        this.commentLines = commentLines;
    }

    public int getBlankLines() {
        return blankLines;
    }

    public void setBlankLines(int blankLines) {
        this.blankLines = blankLines;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }
}