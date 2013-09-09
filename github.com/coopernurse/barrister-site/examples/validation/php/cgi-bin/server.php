#!/usr/bin/env php
<?php

$path = $_ENV["BARRISTER_PHP"];
include_once("$path/barrister.php");

function _debug($s) {
    file_put_contents('php://stderr', "$s\n");
}

function now_millis() {
    return time() * 1000;
}

function create_page($authorId, $title, $body, $category, $publishTime=null) {
    $now_ms = now_millis();
    return (object) array(
             "id"          => uniqid("", true),
             "version"     => 1,
             "createdTime" => $now_ms,
             "updatedTime" => $now_ms,
             "authorId"    => $authorId,
             "title"       => $title,
             "body"        => $body,
             "category"    => $category,
             "publishTime" => $publishTime );
}

class ContentService {

    function __construct() {
        $this->_load();
    }

    function addPage($authorId, $title, $body, $category) {
        $page = create_page($authorId, $title, $body, $category);
        $id = $page->id;
        $this->pagesById->$id = $page;
        $this->_save();
        return $id;
    }

    function updatePage($page) {
        $id = $page->id;
        $existing = $this->getPage($id);
        if (!$existing) {
            throw new BarristerRpcException(40, "No page exists with id: $id");
        }
        elseif ($existing->version !== $page->version) {
            throw new BarristerRpcException(30, "Version out of date: $page->version != $existing->version");
        }
        else {
            $version = $existing->version + 1;
            $page->version     = $version;
            $page->createdTime = $existing->createdTime;
            $page->updatedTime = now_millis();
            $this->pagesById->$id = $page;
            $this->_save();
            return $version;
        }
    }

    function deletePage($id, $version) {
        $existing = $this->getPage($id);
        if ($existing) {
            if ($existing->version === $version) {
                unset($this->pagesById->$id);
                $this->_save();
                return true;
            }
            else {
                throw new BarristerRpcException(30, "Version out of date");
            }
        }
        else {
            return false;
        }
    }

    function getPage($id) {
        return $this->pagesById->$id;
    }

    function _save() {
        file_put_contents("content.json", json_encode($this->pagesById));
    }

    function _load() {
        if (!file_exists("content.json")) {
            $this->pagesById = (object) array();
            return;
        }

        $data = file_get_contents("content.json");
        if ($data === false) {
            $this->pagesById = (object) array();
        }
        else {
            $this->pagesById = json_decode($data, false);
        }
    }

}

$server = new BarristerServer("../validation.json");
$server->addHandler("ContentService", new ContentService());
$server->handleHTTP();
?>