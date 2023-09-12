import { ManageUserComponent } from './manage-user/manage-user.component';
import { Routes } from '@angular/router';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { ManageContratComponent } from './manage-contrat/manage-contrat.component';
import { RouteGuardService } from '../services/route-guard.service';


export const MaterialRoutes: Routes = [
    {
        path:'contrat',
        component:ManageContratComponent,
        canActivate:[RouteGuardService],
        data:{
            expectedRole: ['admin']

        }
    },
    {
        path:'user',
        component:ManageUserComponent,
        canActivate:[RouteGuardService],
        data:{
            expectedRole: ['admin']

        }
    }
];
