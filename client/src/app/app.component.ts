import {Component, OnInit} from '@angular/core';
import {ClientRecord} from "../model/ClientRecord";
import {HttpClient} from "@angular/common/http";
import {environment} from '../environments/environment';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  recordList: ClientRecord[] = [];
  env = environment;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {

    console.log('asd ', this.env.serverPrefix + '/test/client-list');

    this.http.get<ClientRecord[]>(this.env.serverPrefix + '/test/client-list').subscribe({
      next: (list: ClientRecord[]) => {
        this.recordList = list;
      }
    });

  }

}
