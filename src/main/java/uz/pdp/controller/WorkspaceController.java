package uz.pdp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.entity.User;
import uz.pdp.entity.Workspace;
import uz.pdp.entity.WorkspaceRole;
import uz.pdp.payload.*;
import uz.pdp.security.CurrentUser;
import uz.pdp.service.interfaces.WorkspaceService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/workspace")
public class WorkspaceController {
    @Autowired
    WorkspaceService workspaceService;

    @PostMapping
    public HttpEntity<?>addWorkspace(@RequestBody WorkspaceDto dto, @CurrentUser User user){
        ApiResponse apiResponse = workspaceService.addWorkspace(dto,user);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public HttpEntity<?>editWorkspace(@PathVariable Long id,@RequestBody WorkspaceDto dto){
        ApiResponse apiResponse = workspaceService.editWorkspace(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/changeOwner/{id}")
    public HttpEntity<?>editWorkspace(@PathVariable Long id, @RequestParam UUID ownerId){
        ApiResponse apiResponse = workspaceService.changeOwnerWorkspace(id,ownerId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public HttpEntity<?>deleteWorkspace(@PathVariable Long id){
        ApiResponse apiResponse = workspaceService.deleteWorkspace(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getWorkspaces")
    public HttpEntity<?> getWorkspaces(@CurrentUser User user){
        List<Workspace> workspaceList = workspaceService.getWorkspaces(user);
        return ResponseEntity.ok(workspaceList);
    }

    @PostMapping("/addOrEditOrRemoveWorkspaceUser/{id}")
    public HttpEntity<?>addOrEditOrRemoveWorkspaceUser(@PathVariable Long id,@RequestBody MemberDto dto){
        ApiResponse apiResponse = workspaceService.adOrEditOrRemoveWorkspaceUser(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/join")
    public HttpEntity<?>joinToWorkspace(@RequestParam Long id, @RequestBody MemberDto dto){
        ApiResponse apiResponse = workspaceService.joinToWorkspace(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @GetMapping("/getWorkspaceRoles")
    public HttpEntity<?> getWorkspaceRoles(){
        List<WorkspaceRole> workspaceList = workspaceService.getWorkspaceRoles();
        return ResponseEntity.ok(workspaceList);
    }

    @PostMapping("/addWorkspaceRole")
    public HttpEntity<?>addWorkspaceRole(@Valid @RequestBody RoleDto dto){
        ApiResponse apiResponse = workspaceService.addWorkspaceRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PostMapping("/addOrRemovePermissions")
    public HttpEntity<?>adOrRemovePermissions(@RequestBody PermissionDto dto){
        ApiResponse apiResponse = workspaceService.addOrRemovePermissions(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }


}
