package com.axxes.traineeship.testing.integration;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/catalogs")
public class CatalogController {

    @GetMapping
    public ResponseEntity<List<Folder>> getCatalogs() {
        List<CatalogItem> catalogItems = List.of(
                new CatalogItem("group_1", "name_1"),
                new CatalogItem("group_1", "name_2"),
                new CatalogItem("group_2", "name_1")
        );

        Map<String, Folder> catalog = new HashMap<>();

        catalogItems.stream()
                .filter(catalogItem -> catalogItem.getGroupName() != null)
                .forEach(catalogItem -> {
                    Folder folder = catalog.computeIfAbsent(catalogItem.getGroupName(), name -> new Folder(null, name));
                    folder.addChildren(Arrays.asList(catalogItem.getName().split("/")));
                });

        return ResponseEntity.ok(
                catalog.values().stream()
                        .sorted(Comparator.comparing(Folder::getName))
                        .collect(toList())
        );
    }

    public static final class Folder {

        private final Folder parent;
        private final String name;
        private final Map<String, Folder> children = new HashMap<>();

        private Folder(Folder parent, String name) {
            this.parent = parent;
            this.name = name;
        }

        private void addChildren(List<String> children) {
            Folder child = this.children.computeIfAbsent(children.get(0), key -> new Folder(this, key));
            if (children.size() > 1) {
                child.addChildren(children.subList(1, children.size()));
            }
        }

        public String getName() {
            return name;
        }

        public String getFullName() {
            if (parent != null) {
                return parent.getFullName() + "/" + name;
            } else {
                return name;
            }
        }

        public List<Folder> getChildren() {
            return children.values().stream()
                    .sorted(Comparator.comparing(Folder::getName))
                    .collect(toList());
        }

    }

}
